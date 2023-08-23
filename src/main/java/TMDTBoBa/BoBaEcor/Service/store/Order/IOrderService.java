package TMDTBoBa.BoBaEcor.Service.store.Order;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.Cart;
import TMDTBoBa.BoBaEcor.Models.Store.Order;
import TMDTBoBa.BoBaEcor.Models.User.User;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    Optional<Order> findById(Integer id);
    List<Order> findByUser(User user);

    List<Order> findAll();

    Optional<Order> findByIdAndUser(Integer id, User user);

    Order save(Order order, Cart cart);

    Order save(Order order);
}
