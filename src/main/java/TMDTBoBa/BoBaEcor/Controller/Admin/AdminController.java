package TMDTBoBa.BoBaEcor.Controller.Admin;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.*;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CategoryService categoryService;
    private final BrandService brandService;

    private final ProductService productService;

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
    public String collections( @Param("q") String q){
        return "admin/Store/manager_collections";
    }
}
