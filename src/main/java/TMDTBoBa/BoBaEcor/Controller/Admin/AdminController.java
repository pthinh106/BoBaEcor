package TMDTBoBa.BoBaEcor.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    @GetMapping("/category")
    public String category(){
        return "admin/Store/manager_category";
    }
}
