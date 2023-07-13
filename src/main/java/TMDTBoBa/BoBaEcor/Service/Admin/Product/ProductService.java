package TMDTBoBa.BoBaEcor.Service.Admin.Product;

import TMDTBoBa.BoBaEcor.Models.Store.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
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
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByStatus(Integer status) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Product addonProduct(Product product) {
        return null;
    }

    @Override
    public Product editProduct(Product product) {
        return null;
    }

    @Override
    public Product hiddenProduct(Integer productId) {
        return null;
    }

    @Override
    public Product removeProduct(Integer productId) {
        return null;
    }
}
