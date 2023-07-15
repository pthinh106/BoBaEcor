package TMDTBoBa.BoBaEcor.Controller.Admin;

import TMDTBoBa.BoBaEcor.Models.Store.Category;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/admin/addon")
public class AddonController {

    @GetMapping(path = "/product")
    public String addonProduct(Model model){
        model.addAttribute("product", new Product());
        return "admin/Store/addon_product";
    }

}
