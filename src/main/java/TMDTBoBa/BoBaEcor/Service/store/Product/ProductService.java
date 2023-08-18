package TMDTBoBa.BoBaEcor.Service.store.Product;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Models.Store.ProductDetail;
import TMDTBoBa.BoBaEcor.Models.Store.ProductImages;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.Store.IProductDetailRepository;
import TMDTBoBa.BoBaEcor.Repository.Store.IProductImagesRepository;
import TMDTBoBa.BoBaEcor.Repository.Store.IProductPageRepository;
import TMDTBoBa.BoBaEcor.Repository.Store.IProductRepository;
import TMDTBoBa.BoBaEcor.Repository.User.IUserRepository;
import TMDTBoBa.BoBaEcor.Utilities.Contains;
import TMDTBoBa.BoBaEcor.Utilities.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import net.bytebuddy.utility.RandomString;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository iProductRepository;
    private final IProductImagesRepository iProductImagesRepository;
    private final IProductDetailRepository iProductDetailRepository;
    private final IProductPageRepository iProductPageRepository;
    private final IUserRepository iUserRepository;
    @Override
    public Optional<Product> findById(Integer productId) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductDetail> findProductDetailById(Integer id) {
        return iProductDetailRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String productName) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findBySlug(String productSlug) {
        return iProductRepository.findProductByProductSlugAndStatus(productSlug,1);
    }

    @Override
    public Optional<Product> findByStatus(Integer status) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return iProductRepository.findAll();
    }

    @Override
    public Optional<ProductDetail> findDetailByProductIdAndColorAndSize(String color,Integer id,String size) {
        Optional<Product> product = iProductRepository.findById(id);
        return product.map(value -> iProductDetailRepository.findProductDetailByProductAndCodeColorAndSize(value, color,size)).orElse(null);
    }

    @Override
    public StoreResponse findPage(Integer page) {
        try {
            int pageSize = 10;
            Pageable pageable = PageRequest.of(page-1,pageSize);
            int totalPages = iProductPageRepository.findAll(pageable).getTotalPages();
            return new StoreResponse(200,"Get Product Page Success",StoreResponse.returnProduct(iProductPageRepository.findAll(pageable).getContent()),
                    iProductPageRepository.findAll(pageable).getContent(),totalPages,page);
        }catch (Exception e){
            return new StoreResponse(501,"Server Error",null,null,null,page);
        }
    }

    @Override
    public Page<Product> findPageHome(Integer page)  {
            int pageSize = 9;
            Pageable pageable = PageRequest.of(page-1,pageSize);
            return iProductPageRepository.findAllByStatus(1,pageable);
    }
    @Override
    @Transactional
    public StoreResponse addonProduct(Product product, MultipartFile[] multipartFiles, Optional<String[]> tSize, Optional<String[]> tColor, Optional<String[]> tCodeColor,Optional<Integer[]> tPrice,
                                      Optional<Integer[]> tSale, Optional<Integer[]> tInventory, Optional<Integer[]> tSolid) throws RuntimeException{
        try {
            if(product.getProductName().isEmpty()){
                throw new RuntimeException("Product name is required");
            }
            if (iProductRepository.existsByProductSlug(Contains.convertToURL(product.getProductName()))){
                throw new RuntimeException("Can't initialize url because it already exists");
            }
            if(product.getProductPrice() == null && product.getNoTypeStatus() == 1){
                throw new RuntimeException("Product price is required");
            }
            Optional<User> user = iUserRepository.findById(1);
            if(user.isPresent()){
                product.setUserCreate(user.get());
                product.setUserUpdate(user.get());
            }
            product.setProductSlug(Contains.convertToURL(product.getProductName()));
            product.setProductCode(RandomString.make(10));
            iProductRepository.save(product);
            Integer totalQuantitySolid = 0;
            Integer totalQuantityInventory = 0;
            if(product.getNoTypeStatus() == 0 && (tSize.isEmpty() || tColor.isEmpty() || tPrice.isEmpty() || tCodeColor.isEmpty())){
                throw new RuntimeException("Color, Size, Price are required");
            }
            if(product.getNoTypeStatus() == 0 && tSale.isPresent() && tInventory.isPresent() && tSolid.isPresent()) {
                List<ProductDetail> productDetails = new ArrayList<>();
                for(int i = 0; i < tSize.get().length; i++){
                    if(tCodeColor.get()[i].isEmpty() ||tColor.get()[i].isEmpty() || tSize.get()[i].isEmpty() || tPrice.get()[i] < 0 ||
                            tInventory.get()[i] < 0 || tSale.get()[i] < 0 || tSolid.get()[i] < 0){
                        throw new RuntimeException("Color, Size, Price are required");
                    }
                    ProductDetail productDetail = new ProductDetail();
                    productDetail.setProduct(product);
                    productDetail.setSize(tSize.get()[i]);
                    productDetail.setColor(tColor.get()[i]);
                    productDetail.setCodeColor(tCodeColor.get()[i]);
                    productDetail.setProductPrice(tPrice.get()[i]);
                    productDetail.setProductPriceSale(tSale.get()[i]);
                    productDetail.setQuantityInventory(tInventory.get()[i]);
                    productDetail.setQuantitySolid(tSolid.get()[i]);
                    productDetails.add(productDetail);
                    if(tSale.get()[i] != 0){
                        productDetail.setSaleStatus(1);
                        if(product.getSaleStatus() != 1){
                            product.setSaleStatus(1);
                            product.setProductPrice(productDetail.getProductPrice());
                            product.setProductPriceSale(productDetail.getProductPriceSale());
                        }
                    }else{
                        productDetail.setSaleStatus(0);
                    }
                    if(tPrice.get()[i] == 0){
                        productDetail.setDetailStatus(0);
                    }else{
                        productDetail.setDetailStatus(1);
                    }
                    totalQuantitySolid += tInventory.get()[i];
                    totalQuantityInventory += tSolid.get()[i];
                }
                if(product.getProductPriceSale() != 1){
                    product.setSaleStatus(0);
                    product.setProductPrice(productDetails.get(0).getProductPrice());
                }
                iProductDetailRepository.saveAll(productDetails);
            }
            List<ProductImages> imagesList = new ArrayList<>();
            for(MultipartFile multipartFile : multipartFiles){
                if(multipartFile.isEmpty()){
                    continue;
                }
                ProductImages productImages = new ProductImages();
                String generatedFileName = product.getProductSlug() + UUID.randomUUID().toString().replace("-", "");
                String urlImg;
                urlImg = FileUploadUtil.upLoadCloud(generatedFileName,multipartFile);
                productImages.setProduct(product);
                productImages.setProductImage(urlImg);
                imagesList.add(productImages);
            }
            if(!imagesList.isEmpty()){
                iProductImagesRepository.saveAll(imagesList);
                product.setProductThumbnail(imagesList.get(0).getProductImage());
            }else{
                throw new RuntimeException("File is empty!");
            }
            if(product.getNoTypeStatus() == 0 ){
                product.setProductPrice(null);
                product.setQuantitySolid(totalQuantitySolid);
                product.setQuantityInventory(totalQuantityInventory);
            }
            iProductRepository.save(product);
            return new StoreResponse(200,"Addon Product Success",null,null,null,null);
        }catch (RuntimeException | IOException e ){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new StoreResponse(500,"Server Error! "+ e.getMessage(),null,null,null,null);
        }
    }

    @Override
    public StoreResponse editProduct(Product product) {
        return null;
    }

    @Override
    public StoreResponse hiddenProduct(Integer productId) {
        return null;
    }

    @Override
    public StoreResponse removeProduct(Integer productId) {
        return null;
    }

    @Override
    public Page<Product> findAllByName(String productName,Integer page) {
        int pageSize = 9;
        Pageable pageable = PageRequest.of(page-1,pageSize);
        return iProductPageRepository.findAllByProductNameContainingAndStatus(productName,1,pageable);
    }


}
