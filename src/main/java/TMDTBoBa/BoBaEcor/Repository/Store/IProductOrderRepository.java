package TMDTBoBa.BoBaEcor.Repository.Store;

import TMDTBoBa.BoBaEcor.Models.Store.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductOrderRepository extends JpaRepository<ProductOrder,Integer> {
}
