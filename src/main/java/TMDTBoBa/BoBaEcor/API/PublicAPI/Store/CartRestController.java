package TMDTBoBa.BoBaEcor.API.PublicAPI.Store;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.Cart;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.CartItem;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.VNPay.VNPayService;
import TMDTBoBa.BoBaEcor.Controller.BaseController;
import TMDTBoBa.BoBaEcor.Models.Store.ProductDetail;
import TMDTBoBa.BoBaEcor.Service.Blog.Channel14RSSReader;
import TMDTBoBa.BoBaEcor.Service.User.UserService;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Order.OrderService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping (path = "/api/v1/cart")
public class CartRestController extends BaseController {


    public CartRestController(PaypalService paypalService, ProductService productService, CategoryService categoryService, BrandService brandService, Channel14RSSReader channel14RSSReader, UserService userService, VNPayService vnPayService, OrderService orderService) {
        super(paypalService, productService, categoryService, brandService, channel14RSSReader, userService, vnPayService, orderService);
    }

    @PostMapping("/addCart")
    public ResponseEntity<StoreResponse> addToCart(@RequestParam("idDetail") Integer idDetail, @RequestParam("quantity") Integer quantity, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException{
        Optional<ProductDetail> productDetail = productService.findProductDetailById(idDetail);
        Cart cart = new Cart();
        Cookie[] cookies = request.getCookies();
        AtomicReference<Boolean> isExistId = new AtomicReference<>(false);
        AtomicReference<Boolean> isExistCookie = new AtomicReference<>(false);
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("cart")){
                isExistCookie.set(true);
                String value = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                cart = new Gson().fromJson(value,Cart.class);
                List<CartItem> cartItemList = cart.getCartItems();
                for(CartItem cartItem : cartItemList){
                    if (cartItem.getProductDetail().getProductDetailId() == idDetail){
                        cartItem.setQuantity(cartItem.getQuantity()+quantity);
                        if(cartItem.getProductDetail().getSaleStatus() == 1){
                            cartItem.setTotalPriceItem(cartItem.getProductDetail().getProductPriceSale() * cartItem.getQuantity());
                            cartItem.getProductDetail().setSize(productDetail.get().getSize());
                        }else{
                            cartItem.setTotalPriceItem(cartItem.getProductDetail().getProductPrice() * cartItem.getQuantity());
                            cartItem.getProductDetail().setSize(productDetail.get().getSize());
                        }
                        isExistId.set(true);
                        break;
                    }
                }
                break;
            }
        }

        if(productDetail.isPresent()){
            if(!isExistId.get() || !isExistCookie.get()){
                CartItem cartItem = new CartItem(productDetail.get(),productDetail.get().getProduct().getProductName(),productDetail.get().getProduct().getProductThumbnail(),quantity,(productDetail.get().getSaleStatus() == 1 ? productDetail.get().getProductPriceSale() * quantity : productDetail.get().getProductPrice()) *  quantity,productDetail.get().getSize());
                List<CartItem> cartItemList = cart.getCartItems();
                if(cartItemList == null) cartItemList = new ArrayList<>();
                cartItemList.add(cartItem);
                cart.setCartItems(cartItemList);
                if(cart.getTotalPrice() == null) cart.setTotalPrice(0);
                cart.setTotalPrice(cart.getTotalPrice() + cartItem.getTotalPriceItem());
            }else{
                if(productDetail.get().getSaleStatus() == 1){
                    cart.setTotalPrice(cart.getTotalPrice() + productDetail.get().getProductPriceSale() * quantity);
                }else{
                    cart.setTotalPrice(cart.getTotalPrice() + productDetail.get().getProductPrice() * quantity);
                }
            }
        }
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(cart);
        String valueDetails = URLEncoder.encode(json, StandardCharsets.UTF_8);
        Cookie cookie = new Cookie("cart", valueDetails);
        cookie.setMaxAge(86400);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body(new StoreResponse(200,"Thêm vào giở hàng thành công!" , cart.getCartItems().size(),true,0,0));

    }

    @PostMapping("/updateCart")
    public ResponseEntity<StoreResponse> updateCart(@RequestParam("detailId") Integer[] idDetail, @RequestParam("quantity") Integer[] quantity, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException{

        Cart cart = new Cart();
        Cookie[] cookies = request.getCookies();
        AtomicReference<Boolean> isExistCookie = new AtomicReference<>(false);
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("cart")){
                isExistCookie.set(true);
                String value = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                cart = new Gson().fromJson(value,Cart.class);
            }
        }
        if(isExistCookie.get() && cart != null){
            for(CartItem cartItem : cart.getCartItems()){
                for(int i = 0; i < idDetail.length; i++){
                    if(cartItem.getProductDetail().getProductDetailId() == idDetail[i]){
                        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getTotalPriceItem());
                        if(quantity[i] == 0) {
                            cart.getCartItems().remove(cartItem);
                            break;
                        }
                        cartItem.setQuantity(quantity[i]);
                        if(cartItem.getProductDetail().getSaleStatus() == 1){
                            cartItem.setTotalPriceItem(cartItem.getProductDetail().getProductPriceSale() * cartItem.getQuantity());
                        }else{
                            cartItem.setTotalPriceItem(cartItem.getProductDetail().getProductPrice() * cartItem.getQuantity());
                        }
                        cart.setTotalPrice(cart.getTotalPrice() + cartItem.getTotalPriceItem());
                        break;
                    }
                }
            }
        }
        if(cart == null) return ResponseEntity.status(HttpStatus.OK).body(new StoreResponse(200,"Bạn chưa có sản phẩm nào!" , cart.getCartItems().size(),true,0,0));
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(cart);
        String valueDetails = URLEncoder.encode(json, StandardCharsets.UTF_8);
        Cookie cookie = new Cookie("cart", valueDetails);
        cookie.setMaxAge(86400);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body(new StoreResponse(200,"Thay đổi thành công!" , cart.getCartItems().size(),true,0,0));

    }
    @PostMapping("/remove/{id}")
    public ResponseEntity<StoreResponse> updateCart(@PathVariable("id") Integer idDetail, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException{

        Cart cart = null;
        Cookie[] cookies = request.getCookies();
        AtomicReference<Boolean> isExistCookie = new AtomicReference<>(false);
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("cart")){
                isExistCookie.set(true);
                String value = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                cart = new Gson().fromJson(value,Cart.class);
            }
        }
        if(isExistCookie.get() && cart != null){
            for(CartItem cartItem : cart.getCartItems()){
                if(cartItem.getProductDetail().getProductDetailId() == idDetail){
                    cart.setTotalPrice(cart.getTotalPrice() - cartItem.getTotalPriceItem());
                    cart.getCartItems().remove(cartItem);
                    break;
                }
            }
        }
        if(cart == null) return ResponseEntity.status(HttpStatus.OK).body(new StoreResponse(200,"Bạn chưa có sản phẩm nào!" , 0,cart,0,0));
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(cart);
        String valueDetails = URLEncoder.encode(json, StandardCharsets.UTF_8);
        if(!cart.getCartItems().isEmpty()){
            Cookie cookie = new Cookie("cart", valueDetails);
            cookie.setMaxAge(86400);
            cookie.setSecure(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        }else{
            Cookie cookie = new Cookie("cart",null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new StoreResponse(200,"Thay đổi thành công!" , cart.getTotalPrice(),cart,0,0));

    }

}
