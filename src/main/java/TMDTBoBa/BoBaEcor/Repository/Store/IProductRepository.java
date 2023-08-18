package TMDTBoBa.BoBaEcor.Repository.Store;

import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product,Integer> {
    boolean existsByProductSlug(String slug);
    Optional<Product> findProductByProductSlugAndStatus(String slug, Integer status);

    List<Product> findAllByBrand(Brand brand);
    List<Product> findAllByProductNameContaining(String productName);
    @Query(value = "SELECT p FROM Product p WHERE p.category.categoryId IN :listProduct")
    List<Product> getAllByListCategoryId(@Param("listProduct") Collection<Integer> listProduct);
}
