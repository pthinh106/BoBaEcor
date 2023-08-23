package TMDTBoBa.BoBaEcor.Service.store.Order;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.Cart;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.CartItem;
import TMDTBoBa.BoBaEcor.Models.Store.Order;
import TMDTBoBa.BoBaEcor.Models.Store.ProductOrder;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.Store.IOrderRepository;
import TMDTBoBa.BoBaEcor.Repository.Store.IProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final IOrderRepository iOrderRepository;
    private final IProductOrderRepository iProductOrderRepository;
    @Override
    public Optional<Order> findById(Integer id) {
        return iOrderRepository.findById(id);
    }

    @Override
    public List<Order> findByUser(User user) {
        return iOrderRepository.findAllByUser(user);
    }

    @Override
    public List<Order> findAll() {
        return iOrderRepository.findAll();
    }

    @Override
    public Optional<Order> findByIdAndUser(Integer id, User user) {
        return iOrderRepository.findByOrderIdAndUser(id,user);
    }

    @Override
    public Order save(Order order, Cart cart) {
        iOrderRepository.save(order);
        List<ProductOrder> productOrders = new ArrayList<>();
        for(CartItem cartItem : cart.getCartItems()){
            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrder(order);
            productOrder.setProductDetail(cartItem.getProductDetail());
            productOrder.setQuantity(cartItem.getQuantity());
            productOrder.setPrice(cartItem.getTotalPriceItem());
            productOrders.add(productOrder);
        }
        iProductOrderRepository.saveAll(productOrders);
        return order;
    }

    @Override
    public Order save(Order order) {
        return iOrderRepository.save(order);
    }
}
