package TMDTBoBa.BoBaEcor.Controller.Home;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Auth.Login.AuthenticationRequest;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.Cart;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.VNPay.VNPayService;
import TMDTBoBa.BoBaEcor.Controller.BaseController;
import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import TMDTBoBa.BoBaEcor.Models.Store.Category;
import TMDTBoBa.BoBaEcor.Models.Store.Order;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.Store.IOrderRepository;
import TMDTBoBa.BoBaEcor.Service.Blog.Channel14RSSReader;
import TMDTBoBa.BoBaEcor.Models.BlogCustom.RSSItem;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Service.User.UserService;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Order.OrderService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import com.google.gson.Gson;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping(path = "")
public class HomeController  extends BaseController {



    public HomeController(PaypalService paypalService, ProductService productService, CategoryService categoryService, BrandService brandService, Channel14RSSReader channel14RSSReader, UserService userService, VNPayService vnPayService, OrderService orderService,
                          IOrderRepository iOrderRepository) {
        super(paypalService, productService, categoryService, brandService, channel14RSSReader, userService, vnPayService, orderService);
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request){
        String rssFeedUrl = "https://vnexpress.net/rss/giai-tri.rss";
        List<RSSItem> items = channel14RSSReader.readRSSFeedTop(rssFeedUrl);
        model.addAttribute("rssFeedUrl", items);
        model.addAttribute("curURL",request.getRequestURI());
        model.addAttribute("top6",productService.findTop6());
        model.addAttribute("ramDomProduct",productService.ramdomProduct());
        return "home/index";
    }
    @GetMapping("/dang-nhap")
    public String login(Model model, HttpServletRequest request,Principal principal){
        if(principal != null) return "redirect:/";;
        model.addAttribute("user",new User());
        model.addAttribute("authen",new AuthenticationRequest());
        return "home/login";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal){
        String username = principal.getName().trim();
        Optional<User> user = userService.findUserByUsername(username);
        if(user.isPresent()){
            model.addAttribute("user",user.get());
            model.addAttribute("order",orderService.findByUser(user.get()));
        }
        return "home/user-account";
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
    public String checkOut(Model model, HttpServletRequest request, HttpServletResponse response,Principal principal){
        Optional<User> user = Optional.empty();
        if(principal != null){
            String username = principal.getName();
            user = userService.findUserByUsername(username);
        }
        Cart cart = new Cart();
        Order order = new Order();
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("cart")){
                String value = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                cart = new Gson().fromJson(value,Cart.class);
                break;
            }
        }
        if (user.isPresent()) {
            order.setAddress(user.get().getAddress());
            order.setFirstName(user.get().getFirstName());
            order.setLastName(user.get().getLastName());
            order.setPhoneNumber(user.get().getPhoneNumber());
            order.setUser(user.get());
        }
        model.addAttribute("user",user);
        model.addAttribute("order",order);
        model.addAttribute("cart", cart);
        return "home/checkout";
    }

    @GetMapping("/processing/order")
    public String processingOrder(@ModelAttribute("order") Order order,HttpServletRequest request, @RequestParam("check_method") String paymentMethod) throws UnknownHostException, UnsupportedEncodingException {
        if(paymentMethod.isEmpty()) return "pages-error-404";
        order.setPayment(paymentMethod);
        System.out.println(order.getPayment());
        Cart cart = new Cart();
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("cart")){
                String value = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                cart = new Gson().fromJson(value,Cart.class);
                break;
            }
        }
        order.setTotal(cart.getTotalPrice());
        order = orderService.save(order,cart);
        if(paymentMethod.equals("paypal")) return paypalService.createPaypal(order,cart);
        if (paymentMethod.equals("vnpay")) return "redirect:" + vnPayService.createPayment(cart.getTotalPrice(),"",order.getOrderId()).getUrl();
        return "pages-error-404";
    }
    @GetMapping("/thanh-toan/thanh-cong")
    public String orderSuccess(@RequestParam("PayerID") String payerId, @RequestParam("paymentId") String paymentId, @RequestParam("guid") Integer guid, Model model) throws PayPalRESTException {
        Payment payment = paypalService.executePayment(paymentId,payerId,guid);
        Optional<Order> order = orderService.findById(guid);
        if(guid == 123) return "home/payment-success";
        if(order.isPresent()){
            order.get().setPaymentStatus(1);
            orderService.save(order.get());
            model.addAttribute("order",order.get());
            model.addAttribute("payment",payment);
            return "home/payment-success";
        }
        return "pages-error-404";

    }
    @GetMapping("/thanh-toan/that-bai")
    public String orderError(@RequestParam("PayerID") String payerId, @RequestParam("paymentId") String paymentId, @RequestParam("guid") Integer guid, Model model) throws PayPalRESTException {
        Payment payment = paypalService.executePayment(paymentId,payerId,guid);
        Optional<Order> order = orderService.findById(guid);
        if(guid == 123) return "home/payment-error";
        if(order.isPresent()){
            order.get().setPaymentStatus(1);
            orderService.save(order.get());
            model.addAttribute("order",order.get());
            model.addAttribute("payment",payment);
            return "home/payment-error";
        }
        return "pages-error-404";
    }
    @GetMapping("/thanh-toan/vnpay")
    public String paymentVNPay(@RequestParam("vnp_ResponseCode") String vnp_ResponseCode, @RequestParam("guid") Integer guid,Model model){
        if(vnp_ResponseCode.equals("00")){
            Optional<Order> order = orderService.findById(guid);
            if(order.isPresent()){
                model.addAttribute("order",order.get());
                return "home/payment-success";
            }
            else {
                return "pages-error-404";
            }
        }else{
            return "home/payment-error";
        }
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
