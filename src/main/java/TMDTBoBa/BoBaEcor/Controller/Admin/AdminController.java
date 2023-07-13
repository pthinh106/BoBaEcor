package TMDTBoBa.BoBaEcor.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }
}
