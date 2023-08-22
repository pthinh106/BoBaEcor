package TMDTBoBa.BoBaEcor.Repository.Store;

import TMDTBoBa.BoBaEcor.Models.Store.Order;
import TMDTBoBa.BoBaEcor.Models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByUser(User user);
}
