package TMDTBoBa.BoBaEcor.Controller.Home;

import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.Controller.BaseController;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "")
public class HomeController  extends BaseController {
    public HomeController(PaypalService paypalService, ProductService productService, CategoryService categoryService, BrandService brandService) {
        super(paypalService, productService, categoryService, brandService);
    }

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
    public String store(Model model,@RequestParam(name = "page",defaultValue = "1") Integer page ){
        Page<Product> products = productService.findPageHome(page);
        model.addAttribute("listProduct",products.getContent());
        model.addAttribute("page",products.getPageable().getPageNumber() + 1);
        model.addAttribute("pageTotal",products.getTotalPages());
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
    @GetMapping("/thanh-toan/thanh-cong")
    public String orderSuccess(@RequestParam("PayerID") String payerId, @RequestParam("paymentId") String paymentId, @RequestParam("guid") String guid, Model model) throws PayPalRESTException {
        Payment payment = paypalService.executePayment(paymentId,payerId,guid);
        model.addAttribute("payment",payment);
        System.out.println(payment);
        return "home/checkout";
    }
    @GetMapping("/tin-tuc")
    public String blog(){
        return "home/blog";
    }

}
