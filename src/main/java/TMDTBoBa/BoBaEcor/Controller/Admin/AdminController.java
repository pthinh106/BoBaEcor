package TMDTBoBa.BoBaEcor.Controller.Admin;

import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import TMDTBoBa.BoBaEcor.Models.Store.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    @GetMapping("/categories")
    public String categories(Model model){
        model.addAttribute("category", new Category());
        return "admin/Store/manager_category";
    }

    @GetMapping("/brands")
    public String brands(Model model){
        model.addAttribute("brand", new Brand());
        return "admin/Store/manager_brands";
    }

    @GetMapping("/collections")
    public String collections(){
        return "admin/Store/manager_collections";
    }
}
