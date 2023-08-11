package TMDTBoBa.BoBaEcor.Controller.Home;

import TMDTBoBa.BoBaEcor.Models.BlogCustom.RSSItem;
import com.rometools.rome.feed.synd.SyndEntry;
import TMDTBoBa.BoBaEcor.Models.BlogCustom.Kenh14RSSReader;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.model.IModel;

import java.util.List;
@Controller
@RequestMapping(path = "")
public class HomeController {

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request){

        model.addAttribute("curURL",request.getRequestURI());
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

    @GetMapping("/thanh-toan-thanh-cong")
    public String checkOut_Success(){
        return "home/payment-success";
    }

    @GetMapping("/thanh-toan-that-bai")
    public String checkOut_Error(){
        return "home/payment-error";
    }

    @Autowired
    private Kenh14RSSReader rssReader;
    @GetMapping("/tin-tuc")
    public String blog(Model model){
        String rssFeedUrl = "https://vnexpress.net/rss/giai-tri.rss";

        List<RSSItem> items = rssReader.readRSSFeed(rssFeedUrl);
        if (items != null) {
            model.addAttribute("items", items);
        } else {
            model.addAttribute("error", "Failed to read RSS feed.");
        }
        return "home/rss";
    }

}
