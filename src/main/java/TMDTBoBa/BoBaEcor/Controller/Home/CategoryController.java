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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping(path = "/categories")
public class CategoryController extends BaseController {


    public CategoryController(PaypalService paypalService, ProductService productService, CategoryService categoryService, BrandService brandService, Channel14RSSReader channel14RSSReader, UserService userService, VNPayService vnPayService, OrderService orderService) {
        super(paypalService, productService, categoryService, brandService, channel14RSSReader, userService, vnPayService, orderService);
    }

    @GetMapping("/{parent}")
    public String parentCategory(Model model , @PathVariable("parent") String parent, HttpServletResponse response, HttpServletRequest request){
        Optional<Category> optionalCategory = categoryService.findBySlug(parent);
        List<Category> categoryList = categoryService.findAll();
        if(optionalCategory.isPresent()){
            List<Category> categories = categoryService.findAllByParentId(optionalCategory.get().getCategoryId());
            List<Brand> brands = brandService.findAll();
            AtomicReference<Collection<Integer>> listId = new AtomicReference<>(new ArrayList<>());
            categories.forEach(category -> listId.get().add(category.getCategoryId()));
            categories.forEach(category -> System.out.println(category.getCategoryId()));
            List<Product> products = productService.findByListId(listId.get());
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
            model.addAttribute("title",optionalCategory.get().getCategoryName());
            model.addAttribute("curURL",request.getRequestURL());
            model.addAttribute("image","/assets/img/boba.jpg");
            model.addAttribute("des","Chào mừng bạn đến với Boba Shop");
            return "home/shop";
//            return "pages-error-404";
        }
        return "pages-error-404";
    }

}
