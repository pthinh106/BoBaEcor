package TMDTBoBa.BoBaEcor.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin/addon")
public class AddonController {

    @GetMapping(path = "/product")
    public String addonProduct(){
        return "admin/Store/addon_product";
    }
}
