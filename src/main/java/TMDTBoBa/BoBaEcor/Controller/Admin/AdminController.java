package TMDTBoBa.BoBaEcor.Controller.Admin;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Auth.Login.AuthenticationRequest;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.VNPay.VNPayService;
import TMDTBoBa.BoBaEcor.Controller.BaseController;
import TMDTBoBa.BoBaEcor.Models.Store.*;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Service.Blog.Channel14RSSReader;
import TMDTBoBa.BoBaEcor.Service.User.UserService;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Order.OrderService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(path = "/admin")
public class AdminController extends BaseController {


    public AdminController(PaypalService paypalService, ProductService productService, CategoryService categoryService, BrandService brandService, Channel14RSSReader channel14RSSReader, UserService userService, VNPayService vnPayService, OrderService orderService) {
        super(paypalService, productService, categoryService, brandService, channel14RSSReader, userService, vnPayService, orderService);
    }

    @GetMapping("")
    public String index(){
        return "admin/index";
    }
    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    @GetMapping("/management/product")
    public String product(Model model){
            StoreResponse productPageResponse = productService.findPage(1);
            model.addAttribute("listProduct", productPageResponse.getDataDetail());
//        model.addAttribute("listProduct", productService.findAll());
            model.addAttribute("totalPage", productPageResponse.getTotalPage());
            model.addAttribute("page", productPageResponse.getPaging());
            System.out.println(productPageResponse.getTotalPage());
            return "admin/Store/manager_product";

    }
    @GetMapping("/update/product/{id}")
    public String updateProduct(@PathVariable Integer id, Model model){
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()){
            model.addAttribute("product",product.get());
            model.addAttribute("listBrand", brandService.findAll());
            model.addAttribute("listCategory", categoryService.findAll());
            return "admin/Store/addon_product";
        }
        return "pages-error-404";
    }

    @GetMapping("/management/categories")
    public String categories(Model model){

            model.addAttribute("category", new Category());
            model.addAttribute("listCategory", categoryService.findAll());
            return "admin/Store/manager_category";

    }

    @GetMapping("/management/brands" )
    public String brands(Model model){

            model.addAttribute("brand", new Brand());
            model.addAttribute("listBrand",brandService.findAll());
            return "admin/Store/manager_brands";

    }

    @GetMapping("/management/orders")
    public String orders(Model model){
        model.addAttribute("orders",orderService.findAll());
        return "admin/Store/manager_orders";

    }
    @GetMapping("/dang-nhap")
    public String login(Principal principal, HttpServletRequest request, HttpServletResponse response){
        if(principal != null) {
            Optional<User> user = Optional.empty();
            String username = principal.getName();
            user = userService.findUserByUsername(username);
            if(user.isPresent() && user.get().getIsEmployee() == 1){
                return "redirect:/admin";
            }else{
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }

        }else{
            return "admin/login";
        }
        return "admin/login";
    }

    @GetMapping("/order/{id}")
    public String orderDetail(@PathVariable Integer id, Model model){
        Optional<Order> order = orderService.findById(id);
        if(order.isPresent()) {
            model.addAttribute("order",order.get());
            return "admin/Store/order-detail";
        }
        return "pages-error-404";
    }

    @GetMapping("/management/collections")
    public String collections(){
        return "admin/Store/manager_collections";
    }
}
