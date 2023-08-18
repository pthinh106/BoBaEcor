package TMDTBoBa.BoBaEcor.Repository.Store;


import TMDTBoBa.BoBaEcor.Models.Store.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsByCategorySlug(String categorySlug);
    Optional<Category> findByCategorySlug(String id);

    List<Category> findAllByCategoryIdOrParentId(Integer categoryId, Integer parentId);
}
