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

        return "home/index";
    }

    @GetMapping("/tim-kiem")
    public String findProduct(Model model){

        return "home/findproduct";
    }

    @GetMapping("/cua-hang")
    public String store(){
        return "home/shop";
    }

    @GetMapping("/gio-hang")
    public String cart(){
        return "home/cart";
    }

    @GetMapping("/thanh-toan")
    public String checkOut(){
        return "home/checkout";
    }
    @GetMapping("/tin-tuc")
    public String blog(){
        return "home/blog";
    }

}
