package TMDTBoBa.BoBaEcor.Controller.Home;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Auth.Login.AuthenticationRequest;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.Cart;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.Controller.BaseController;
import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import TMDTBoBa.BoBaEcor.Models.Store.Category;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Service.Blog.Channel14RSSReader;
import TMDTBoBa.BoBaEcor.Models.BlogCustom.RSSItem;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Service.User.UserService;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import com.google.gson.Gson;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping(path = "")
public class HomeController  extends BaseController {


    public HomeController(PaypalService paypalService, ProductService productService, CategoryService categoryService, BrandService brandService, Channel14RSSReader channel14RSSReader, UserService userService) {
        super(paypalService, productService, categoryService, brandService, channel14RSSReader, userService);
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request, Principal principal){
        String rssFeedUrl = "https://vnexpress.net/rss/giai-tri.rss";
        List<RSSItem> items = channel14RSSReader.readRSSFeedTop(rssFeedUrl);
        model.addAttribute("rssFeedUrl", items);
        model.addAttribute("curURL",request.getRequestURI());
        model.addAttribute("top6",productService.findTop6());
        model.addAttribute("ramDomProduct",productService.ramdomProduct());
        return "home/index";
    }
    @GetMapping("/dang-nhap")
    public String login(Model model, HttpServletRequest request){
        model.addAttribute("user",new User());
        model.addAttribute("authen",new AuthenticationRequest());
        return "home/login";
    }

    @GetMapping("/user")
    public String user(){
        return "home/payment-success";
    }
    @GetMapping("/cua-hang")
    public String store(Model model, @RequestParam(name = "q",defaultValue = "") String productName,@RequestParam(name = "page",defaultValue = "1") Integer page,
                              HttpServletRequest request, HttpServletResponse response){
        Page<Product> products = productService.findAllByName(productName,page);
        List<Category> categories = categoryService.findAll();
        List<Brand> brands = brandService.findAll();
        AtomicReference<List<Category>> parent = new AtomicReference<>(new ArrayList<>());
        for(Category category : categories){
            if(category.getParentId() == 0) parent.get().add(category);
        }
        model.addAttribute("categories",categories);
        model.addAttribute("brands",brands);
        model.addAttribute("parent",parent.get());
        model.addAttribute("listProduct",products.getContent());
        model.addAttribute("page",products.getPageable().getPageNumber() + 1);
        model.addAttribute("pageTotal",products.getTotalPages());
        model.addAttribute("curURL",request.getRequestURL());
        model.addAttribute("baseUrl", ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString());

        return "home/shop";
    }

    @GetMapping("/gio-hang")
    public String cart(Model model, HttpServletRequest request, HttpServletResponse response){
        Cart cart = new Cart();
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("cart")){
                String value = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                cart = new Gson().fromJson(value,Cart.class);
                break;
            }
        }
        model.addAttribute("cart", cart);
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
    public String blog(Model model){
        String rssFeedUrl = "https://vnexpress.net/rss/giai-tri.rss";
        List<RSSItem> items = channel14RSSReader.readRSSFeed(rssFeedUrl);
        model.addAttribute("items", items);
        return "home/blog";
    }

    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("code") String code, Model model){
        boolean verify = userService.verifyUser(code);
        if(!verify){
            return "pages-error-404";
        }
        model.addAttribute("success",verify);
        return "home/verify";
    }
    @GetMapping( value = "/robots.txt")
    public String robot(HttpServletResponse response,Model model) throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getResource("/static/robots.txt")).toURI());
        String value = new String(Files.readAllBytes(path));
        model.addAttribute("value", value);
        return "robot";
    }




}
