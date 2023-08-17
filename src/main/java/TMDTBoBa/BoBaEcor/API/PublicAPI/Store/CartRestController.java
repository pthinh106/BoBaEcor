package TMDTBoBa.BoBaEcor.API.PublicAPI.Store;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.Cart;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.CartItem;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.Controller.BaseController;
import TMDTBoBa.BoBaEcor.Models.Store.ProductDetail;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public CartRestController(PaypalService paypalService, ProductService productService, CategoryService categoryService, BrandService brandService) {
        super(paypalService, productService, categoryService, brandService);
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
                CartItem cartItem = new CartItem(productDetail.get(),quantity,(productDetail.get().getSaleStatus() == 1 ? productDetail.get().getProductPriceSale() * quantity : productDetail.get().getProductPrice()) *  quantity);
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
        String json = objectWriter.writeValueAsString(cart).replace(" ","");
        String valueDetails = URLEncoder.encode(json, StandardCharsets.UTF_8);
        Cookie cookie = new Cookie("cart", valueDetails);
        cookie.setMaxAge(86400);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body(new StoreResponse(200,"Thêm vào giở hàng thành công!" , true,true,0,0));

    }

}
