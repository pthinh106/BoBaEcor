package TMDTBoBa.BoBaEcor.Controller.Home;

import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.VNPay.VNPayService;
import TMDTBoBa.BoBaEcor.Controller.BaseController;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Service.Blog.Channel14RSSReader;
import TMDTBoBa.BoBaEcor.Service.User.UserService;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Order.OrderService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@Controller
@RequestMapping(path = "/san-pham")
public class ProductController extends BaseController {


    public ProductController(PaypalService paypalService, ProductService productService, CategoryService categoryService, BrandService brandService, Channel14RSSReader channel14RSSReader, UserService userService, VNPayService vnPayService, OrderService orderService) {
        super(paypalService, productService, categoryService, brandService, channel14RSSReader, userService, vnPayService, orderService);
    }

    @GetMapping("/{slug}")
    public String getProductDetailsBySlung(@PathVariable String slug, Model model, HttpServletRequest request){
        Optional<Product> productOption = productService.findBySlug(slug);
        if(productOption.isPresent()){
            model.addAttribute("ramDomProduct",productService.ramdomProduct());
            model.addAttribute("product", productOption.get());
            model.addAttribute("productFirstColor", productOption.get().getItemColor().iterator().next());
            model.addAttribute("productFirstSize", productOption.get().getItemSize().iterator().next());
            model.addAttribute("curURL",request.getRequestURL());
            model.addAttribute("baseUrl", ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString());
        }
        return "home/product-details";

    }
}
