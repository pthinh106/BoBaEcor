package TMDTBoBa.BoBaEcor.Controller.Home;

import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.VNPay.VNPayService;
import TMDTBoBa.BoBaEcor.Controller.BaseController;
import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import TMDTBoBa.BoBaEcor.Models.Store.Category;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Service.Blog.Channel14RSSReader;
import TMDTBoBa.BoBaEcor.Service.User.UserService;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Order.OrderService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping(path = "/brands")
public class BrandController extends BaseController {


    public BrandController(PaypalService paypalService, ProductService productService, CategoryService categoryService, BrandService brandService, Channel14RSSReader channel14RSSReader, UserService userService, VNPayService vnPayService, OrderService orderService) {
        super(paypalService, productService, categoryService, brandService, channel14RSSReader, userService, vnPayService, orderService);
    }

    @GetMapping("/{slug}")
    public String parentCategory(Model model , @PathVariable("slug") String slug, HttpServletResponse response, HttpServletRequest request){
        Optional<Brand> brandOptional = brandService.findBySlug(slug);

        if(brandOptional.isPresent()){
            List<Category> categoryList = categoryService.findAll();
            List<Brand> brands = brandService.findAll();
            List<Product> products = productService.findAllByBrand(brandOptional.get());
            AtomicReference<List<Category>> categoryParent = new AtomicReference<>(new ArrayList<>());
            for(Category category : categoryList){
                if(category.getParentId() == 0) categoryParent.get().add(category);
            }
            model.addAttribute("categories",categoryList);
            model.addAttribute("brands",brands);
            model.addAttribute("parent",categoryParent.get());
            model.addAttribute("listProduct",products);
            model.addAttribute("page",0);
            model.addAttribute("pageTotal",0);
            model.addAttribute("curURL",request.getRequestURL());
            model.addAttribute("baseUrl", ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString());
            return "home/shop";
//            return "pages-error-404";
        }
        return "pages-error-404";
    }
}
