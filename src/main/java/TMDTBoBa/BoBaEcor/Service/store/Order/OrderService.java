package TMDTBoBa.BoBaEcor.Service.store.Order;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.Cart;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.CartItem;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Order;
import TMDTBoBa.BoBaEcor.Models.Store.ProductOrder;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.Store.IOrderRepository;
import TMDTBoBa.BoBaEcor.Repository.Store.IProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.JdkConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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

    @Override
    @Transactional
    public StoreResponse orderSuccess(Integer id) {
        try {
            Optional<Order> optionalOrder = iOrderRepository.findById(id);
            if(optionalOrder.isPresent()){
                optionalOrder.get().setStatus(1);
                iOrderRepository.save(optionalOrder.get());
                return new StoreResponse(200,"Update success!",optionalOrder,optionalOrder,0,0);
            }else {
                throw new RuntimeException("Server Error");
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new StoreResponse(500,e.getMessage(),null,null,0,0);
        }
    }

    @Override
    public StoreResponse delivered(Integer id) {
        try {
            Optional<Order> optionalOrder = iOrderRepository.findById(id);
            if(optionalOrder.isPresent()){
                optionalOrder.get().setStatus(0);
                optionalOrder.get().setPaymentStatus(0);
                iOrderRepository.save(optionalOrder.get());
                return new StoreResponse(200,"Delivered success!",optionalOrder,optionalOrder,0,0);
            }else {
                throw new RuntimeException("Server Error");
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new StoreResponse(500,e.getMessage(),null,null,0,0);
        }
    }
}
