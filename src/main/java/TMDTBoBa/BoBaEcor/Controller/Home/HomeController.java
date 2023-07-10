package TMDTBoBa.BoBaEcor.Controller.Home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "")
public class HomeController {
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("sos","sos");

        return "home/index";
    }
    @GetMapping("/thoi-trang-nam")
    public String store(){
        return "home/store";
    }

}
