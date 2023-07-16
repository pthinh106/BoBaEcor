package TMDTBoBa.BoBaEcor.Controller.Admin;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import TMDTBoBa.BoBaEcor.Models.Store.Category;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Service.Admin.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.Admin.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.Admin.Product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    public String product(Model model){
        StoreResponse productPageResponse = productService.findPage(1);
        model.addAttribute("listProduct", productPageResponse.getDataDetail());
//        model.addAttribute("listProduct", productService.findAll());
        model.addAttribute("totalPage", productPageResponse.getTotalPage());
        model.addAttribute("page", productPageResponse.getPaging());
        System.out.println(productPageResponse.getTotalPage());
        return "admin/Store/manager_product";
    }

    @GetMapping("/management/categories")
    public String categories(Model model){
        model.addAttribute("category", new Category());
        model.addAttribute("listCategory", categoryService.findAll());

        return "admin/Store/manager_category";
    }

    @GetMapping("/management/brands")
    public String brands(Model model){
        model.addAttribute("brand", new Brand());
        model.addAttribute("listBrand",brandService.findAll());
        return "admin/Store/manager_brands";
    }

    @GetMapping("/management/collections")
    public String collections(){
        return "admin/Store/manager_collections";
    }
}
