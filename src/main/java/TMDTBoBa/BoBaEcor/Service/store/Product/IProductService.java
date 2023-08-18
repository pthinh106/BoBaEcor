package TMDTBoBa.BoBaEcor.Service.store.Product;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Models.Store.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Optional<Product> findById(Integer productId);

    Optional<ProductDetail> findProductDetailById(Integer id);
    Optional<Product> findByName(String productName);
    Optional<Product> findBySlug(String productSlug);
    Optional<Product> findByStatus(Integer status);
    List<Product> findAll();

    Optional<ProductDetail> findDetailByProductIdAndColorAndSize(String color,Integer id,String size);;
    StoreResponse findPage(Integer page);
    Page<Product> findPageHome(Integer page);
    StoreResponse addonProduct(Product product, MultipartFile[] multipartFiles, Optional<String[]>  tSize, Optional<String[]> tColor,Optional<String[]> tCodeColor,
                         Optional<Integer[]> tPrice, Optional<Integer[]> tSale, Optional<Integer[]> tInventory, Optional<Integer[]> tSolid);

    StoreResponse editProduct(Product product);
    StoreResponse hiddenProduct(Integer productId);
    StoreResponse removeProduct(Integer productId);
}
