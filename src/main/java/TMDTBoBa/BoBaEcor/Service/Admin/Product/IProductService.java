package TMDTBoBa.BoBaEcor.Service.Admin.Product;

import TMDTBoBa.BoBaEcor.Models.Store.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Optional<Product> findById(Integer productId);
    Optional<Product> findByName(String productName);
    Optional<Product> findBySlug(String productSlug);
    Optional<Product> findByStatus(Integer status);
    List<Product> findAll();
    Product addonProduct(Product product);
    Product editProduct(Product product);
    Product hiddenProduct(Integer productId);
    Product removeProduct(Integer productId);
}
