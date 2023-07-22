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
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import net.bytebuddy.utility.RandomString;

import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLTransactionRollbackException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Transactional
    public StoreResponse addonProduct(Product product, MultipartFile[] multipartFiles, Optional<String[]> tSize, Optional<String[]> tColor, Optional<String[]> tCodeColor,Optional<Integer[]> tPrice,
                                      Optional<Integer[]> tSale, Optional<Integer[]> tInventory, Optional<Integer[]> tSolid) throws RuntimeException{
        try {
            if(product.getProductName().isEmpty()){
                return new StoreResponse(500,"Product name is required",null,null,null,null);
            }
            if (iProductRepository.existsByProductSlug(Contains.convertToURL(product.getProductName()))){
                return new StoreResponse(500,"Can't initialize url because it already exists",null,null,null,null);
            }
            if(product.getProductPrice() == null && product.getNoTypeStatus() == 1){
                return new StoreResponse(500,"Product price is required",null,null,null,null);
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
            Integer productSale = 0;
            if(product.getNoTypeStatus() == 0 && (tSize.isEmpty() || tColor.isEmpty() || tPrice.isEmpty() || tCodeColor.isEmpty())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new StoreResponse(500,"Color, Size, Price are required",null,null,null,null);
            }
            if(product.getNoTypeStatus() == 0 && tSize.isPresent() && tColor.isPresent() && tCodeColor.isPresent() && tPrice.isPresent() && tSale.isPresent() && tInventory.isPresent() && tSolid.isPresent()) {
                List<ProductDetail> productDetails = new ArrayList<>();
                for(int i = 0; i < tSize.get().length; i++){
                    if(tCodeColor.get()[i].isEmpty() ||tColor.get()[i].isEmpty() || tSize.get()[i].isEmpty() || tPrice.get()[i] < 0 ||
                            tInventory.get()[i] < 0 || tSale.get()[i] < 0 || tSolid.get()[i] < 0){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return new StoreResponse(500,"Color, Size, Price are required and remaining values are not negative",null,null,null,null);
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
                        productSale = 1;
                    }
                    if(tPrice.get()[i] == 0){
                        productDetail.setDetailStatus(0);
                    }
                    totalQuantitySolid += tInventory.get()[i];
                    totalQuantityInventory += tSolid.get()[i];
                }
                iProductDetailRepository.saveAll(productDetails);
            }
//            List<ProductImages> imagesList = new ArrayList<>();
//            for(MultipartFile multipartFile : multipartFiles){
//                if(multipartFile.isEmpty()){
//                    continue;
//                }
//                ProductImages productImages = new ProductImages();
//                String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
//                String generatedFileName = UUID.randomUUID().toString().replace("-", "");
//                generatedFileName = generatedFileName+"."+fileExtension;
//                String uploadDir = "/src/main/resources/static/images/product" + "/"+ product.getProductId();
//                String urlImg = new String();
//                urlImg = "/images/product/" + product.getProductId() +"/"+ generatedFileName;
//                FileUploadUtil.saveFile(uploadDir,generatedFileName,multipartFile);
//                productImages.setProduct(product);
//                productImages.setProductImage(urlImg);
//                imagesList.add(productImages);
//            }
//            if(!imagesList.isEmpty()){
//                iProductImagesRepository.saveAll(imagesList);
//                product.setProductThumbnail(imagesList.get(0).getProductImage());
//            }else{
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                return new StoreResponse(500,"File is empty!",null,null,null,null);
//            }
//            if(product.getNoTypeStatus() == 0 ){
//                product.setProductPrice(null);
//                product.setQuantitySolid(totalQuantitySolid);
//                product.setQuantityInventory(totalQuantityInventory);
//            }
//            if(product.getProductPriceSale() != null || productSale == 1){
//                product.setSaleStatus(1);
//            }
//            | IOException
//            iProductRepository.save(product);
            return new StoreResponse(200,"Addon Product Success",null,null,null,null);
        }catch (TransactionException  e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new StoreResponse(500,"Server Error! ",null,null,null,null);
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
}
