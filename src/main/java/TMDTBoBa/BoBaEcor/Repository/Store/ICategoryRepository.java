package TMDTBoBa.BoBaEcor.Repository.Store;


import TMDTBoBa.BoBaEcor.Models.Store.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Integer> {
}
