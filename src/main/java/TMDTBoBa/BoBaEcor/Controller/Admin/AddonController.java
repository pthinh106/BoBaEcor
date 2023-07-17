package TMDTBoBa.BoBaEcor.Controller.Admin;

import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/admin/addon")
@RequiredArgsConstructor
public class AddonController {
    private final BrandService brandService;
    private final CategoryService categoryService;

    @GetMapping(path = "/product")
    public String addonProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("listBrand", brandService.findAll());
        model.addAttribute("listCategory", categoryService.findAll());
        return "admin/Store/addon_product";
    }

}
