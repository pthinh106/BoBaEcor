package TMDTBoBa.BoBaEcor.Repository.Store;

import TMDTBoBa.BoBaEcor.Models.Store.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product,Integer> {
    boolean existsByProductSlug(String slug);
}
