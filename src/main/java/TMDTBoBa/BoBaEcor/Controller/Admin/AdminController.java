package TMDTBoBa.BoBaEcor.Controller.Admin;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.Controller.BaseController;
import TMDTBoBa.BoBaEcor.Models.Store.*;
import TMDTBoBa.BoBaEcor.Service.Blog.Channel14RSSReader;
import TMDTBoBa.BoBaEcor.Service.User.UserService;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping(path = "/admin")
public class AdminController extends BaseController {


    public AdminController(PaypalService paypalService, ProductService productService, CategoryService categoryService, BrandService brandService, Channel14RSSReader channel14RSSReader, UserService userService) {
        super(paypalService, productService, categoryService, brandService, channel14RSSReader, userService);
    }

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    @GetMapping("/management/product")
    public String product(Model model , @Param("q") String q){
        if(Objects.equals(q, "admin")){
            StoreResponse productPageResponse = productService.findPage(1);
            model.addAttribute("listProduct", productPageResponse.getDataDetail());
//        model.addAttribute("listProduct", productService.findAll());
            model.addAttribute("totalPage", productPageResponse.getTotalPage());
            model.addAttribute("page", productPageResponse.getPaging());
            System.out.println(productPageResponse.getTotalPage());
            return "admin/Store/manager_product";
        }
        return "redirect:/error";
    }

    @GetMapping("/management/categories")
    public String categories(Model model, @Param("q") String q){
        if(Objects.equals(q, "admin")){
            model.addAttribute("category", new Category());
            model.addAttribute("listCategory", categoryService.findAll());
            return "admin/Store/manager_category";
        }
        return "redirect:/error";
    }

    @GetMapping("/management/brands" )
    public String brands(Model model, @Param("q") String q){
        if(Objects.equals(q, "admin")){
            model.addAttribute("brand", new Brand());
            model.addAttribute("listBrand",brandService.findAll());
            return "admin/Store/manager_brands";}
        return "redirect:/error";
    }


    @GetMapping("/management/collections")
    public String collections(){
        return "admin/Store/manager_collections";
    }
}
