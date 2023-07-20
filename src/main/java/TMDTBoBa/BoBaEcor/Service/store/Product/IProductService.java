package TMDTBoBa.BoBaEcor.Service.store.Product;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Optional<Product> findById(Integer productId);
    Optional<Product> findByName(String productName);
    Optional<Product> findBySlug(String productSlug);
    Optional<Product> findByStatus(Integer status);
    List<Product> findAll();
    StoreResponse findPage(Integer page);
    StoreResponse addonProduct(Product product, MultipartFile[] multipartFiles, Optional<String[]>  tSize, Optional<String[]> tColor,Optional<String[]> tCodeColor,
                         Optional<Integer[]> tPrice, Optional<Integer[]> tSale, Optional<Integer[]> tInventory, Optional<Integer[]> tSolid);

    StoreResponse editProduct(Product product);
    StoreResponse hiddenProduct(Integer productId);
    StoreResponse removeProduct(Integer productId);
}
