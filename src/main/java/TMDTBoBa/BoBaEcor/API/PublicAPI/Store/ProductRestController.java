package TMDTBoBa.BoBaEcor.API.PublicAPI.Store;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/product")
public class ProductRestController extends BaseController {


    public ProductRestController(PaypalService paypalService, ProductService productService, CategoryService categoryService, BrandService brandService, Channel14RSSReader channel14RSSReader, UserService userService, VNPayService vnPayService, OrderService orderService) {
        super(paypalService, productService, categoryService, brandService, channel14RSSReader, userService, vnPayService, orderService);
    }

    @GetMapping("/detail")
    public ResponseEntity<ProductDetail> getProductDetail(@RequestParam("color") String color, @RequestParam("productId") Integer id,@RequestParam("size") String size){
        Optional<ProductDetail> productDetail = productService.findDetailByProductIdAndColorAndSize(color, id,size);
        return productDetail.map(detail -> ResponseEntity.ok().body(detail)).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
