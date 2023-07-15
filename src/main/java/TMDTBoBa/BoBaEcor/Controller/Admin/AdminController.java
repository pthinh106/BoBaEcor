package TMDTBoBa.BoBaEcor.Controller.Admin;

import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import TMDTBoBa.BoBaEcor.Models.Store.Category;
import TMDTBoBa.BoBaEcor.Service.Admin.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.Admin.Category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CategoryService categoryService;
    private final BrandService brandService;

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    @GetMapping("/manager/product")
    public String product(Model model){
        return "admin/Store/manager_product";
    }

    @GetMapping("/manager/categories")
    public String categories(Model model){
        model.addAttribute("category", new Category());
        model.addAttribute("listCategory", categoryService.findAll());

        return "admin/Store/manager_category";
    }

    @GetMapping("/manager/brands")
    public String brands(Model model){
        model.addAttribute("brand", new Brand());
        model.addAttribute("listBrand",brandService.findAll());
        return "admin/Store/manager_brands";
    }

    @GetMapping("/manager/collections")
    public String collections(){
        return "admin/Store/manager_collections";
    }
}
