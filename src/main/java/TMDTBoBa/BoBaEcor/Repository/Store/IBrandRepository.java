package TMDTBoBa.BoBaEcor.Repository.Store;

import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBrandRepository extends JpaRepository<Brand,Integer> {
    boolean existsByBrandSlug(String brandSlug);

    Optional<Brand> findByBrandSlug(String slug);
}
