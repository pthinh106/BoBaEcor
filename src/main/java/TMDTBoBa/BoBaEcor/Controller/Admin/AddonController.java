package TMDTBoBa.BoBaEcor.Controller.Admin;

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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping(path = "/admin/addon")
public class AddonController extends BaseController {


    public AddonController(PaypalService paypalService, ProductService productService, CategoryService categoryService, BrandService brandService, Channel14RSSReader channel14RSSReader, UserService userService, VNPayService vnPayService, OrderService orderService) {
        super(paypalService, productService, categoryService, brandService, channel14RSSReader, userService, vnPayService, orderService);
    }

    @GetMapping(path = "/product")
    public String addonProduct(Model model, @Param("q") String q){
        if(Objects.equals(q, "admin")){
            model.addAttribute("product", new Product());
            model.addAttribute("listBrand", brandService.findAll());
            model.addAttribute("listCategory", categoryService.findAll());
            return "admin/Store/addon_product";
        }
        return "redirect:/error";
    }

}
