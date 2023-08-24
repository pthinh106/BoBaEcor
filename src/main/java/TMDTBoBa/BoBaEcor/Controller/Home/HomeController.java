package TMDTBoBa.BoBaEcor.Controller.Home;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Auth.Login.AuthenticationRequest;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.Cart;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.VNPay.VNPayService;
import TMDTBoBa.BoBaEcor.Controller.BaseController;
import TMDTBoBa.BoBaEcor.Models.Store.*;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.Store.IOrderRepository;
import TMDTBoBa.BoBaEcor.Service.Blog.Channel14RSSReader;
import TMDTBoBa.BoBaEcor.Models.BlogCustom.RSSItem;
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
        model.addAttribute("title","BobaEcor Shop");
        model.addAttribute("curURL",request.getRequestURL());
        model.addAttribute("image","/assets/img/boba.jpg");
        model.addAttribute("des","Chào mừng bạn đến với Boba Shop");
        return "home/index";
    }
    @GetMapping("/dang-nhap")
    public String login(Model model, HttpServletRequest request,Principal principal){
        if(principal != null) return "redirect:/";;
        model.addAttribute("user",new User());
        model.addAttribute("authen",new AuthenticationRequest());
        model.addAttribute("title","Đăng Nhập");
        model.addAttribute("curURL",request.getRequestURL());
        model.addAttribute("image","/assets/img/boba.jpg");
        model.addAttribute("des","Chào mừng bạn đến với Boba Shop");
        return "home/login";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal,HttpServletRequest request){
        String username = principal.getName().trim();
        Optional<User> user = userService.findUserByUsername(username);
        if(user.isPresent()){
            model.addAttribute("user",user.get());
            model.addAttribute("order",orderService.findByUser(user.get()));
        }
        model.addAttribute("title","Trang Cá Nhân");
        model.addAttribute("curURL",request.getRequestURL());
        model.addAttribute("image","/assets/img/boba.jpg");
        model.addAttribute("des","Chào mừng bạn đến với Boba Shop");
        return "home/user-account";
    }
    @GetMapping("/user/order/{id}")
    public String userOrderDetails(Model model, Principal principal, HttpServletRequest request, @PathVariable Integer id){
        String username = principal.getName().trim();
        Optional<User> user = userService.findUserByUsername(username);
        if(user.isPresent()){
            Optional<Order> orderDetail = orderService.findByIdAndUser(id,user.get());
            if(orderDetail.isPresent()){
                model.addAttribute("orderDetail",orderDetail.get());
                model.addAttribute("order",new ArrayList<>());
                model.addAttribute("user",user.get());
                model.addAttribute("title","Chi tiết đặt hàng");
                model.addAttribute("curURL",request.getRequestURL());
                model.addAttribute("image","/assets/img/boba.jpg");
                model.addAttribute("des","Chào mừng bạn đến với Boba Shop");
                return "home/order-detail";
            }
        }

        return "pages-error-404";
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
        model.addAttribute("page",page);
        model.addAttribute("pageTotal",products.getTotalPages());
        model.addAttribute("title","Cửa Hàng");
        model.addAttribute("curURL",request.getRequestURL());
        model.addAttribute("image","/assets/img/boba.jpg");
        model.addAttribute("des","Chào mừng bạn đến với Boba Shop");

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
        model.addAttribute("title","Giỏ hàng");
        model.addAttribute("curURL",request.getRequestURL());
        model.addAttribute("image","/assets/img/boba.jpg");
        model.addAttribute("des","Chào mừng bạn đến với Boba Shop");
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
        if (cart.getCartItems() == null) return "redirect:/cua-hang";
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
        model.addAttribute("title","Thanh toán");
        model.addAttribute("curURL",request.getRequestURL());
        model.addAttribute("image","/assets/img/boba.jpg");
        model.addAttribute("des","Chào mừng bạn đến với Boba Shop");
        return "home/checkout";
    }

    @GetMapping("/processing/order")
    public String processingOrder(@ModelAttribute("order") Order order,HttpServletRequest request, @RequestParam("check_method") String paymentMethod,Principal principal) throws UnknownHostException, UnsupportedEncodingException {
        if(paymentMethod.isEmpty()) return "pages-error-404";
        order.setPayment(paymentMethod);
        Cart cart = new Cart();
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("cart")){
                String value = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                cart = new Gson().fromJson(value,Cart.class);
                break;
            }
        }
        Optional<User> user = Optional.empty();
        if(principal != null){
            String username = principal.getName();
            user = userService.findUserByUsername(username);
        }
        if(user.isPresent()) order.setUser(user.get());
        if(order.getAddress().isEmpty() || order.getFirstName().isEmpty() || order.getLastName().isEmpty() || order.getPhoneNumber().isEmpty()) return "pages-error-404";
        order.setTotal(cart.getTotalPrice());
        order = orderService.save(order,cart);
        if(paymentMethod.equals("paypal")) return paypalService.createPaypal(order,cart);
        if (paymentMethod.equals("vnpay")) return "redirect:" + vnPayService.createPayment(cart.getTotalPrice(),"",order.getOrderId()).getUrl();
        return "pages-error-404";
    }
    @GetMapping("/thanh-toan/thanh-cong")
    public String orderSuccess(@RequestParam("PayerID") String payerId, @RequestParam("paymentId") String paymentId, @RequestParam("guid") Integer guid, Model model,HttpServletRequest request, HttpServletResponse response) throws PayPalRESTException {
        if(guid == 0) return "home/payment-success";
        Payment payment = paypalService.executePayment(paymentId,payerId,guid);
        Optional<Order> order = orderService.findById(guid);
        if(order.isPresent()){
            User user = order.get().getUser();
            if(user != null){
                order.get().setPaymentStatus(1);
                order.get().getUser().setTotalPrice( (order.get().getUser().getTotalPrice() + order.get().getTotal()));
                userService.save(order.get().getUser());
            }
            orderService.save(order.get());
            model.addAttribute("order",order.get());
            model.addAttribute("payment",payment);
            model.addAttribute("title","Thanh toán thành công");
            model.addAttribute("curURL",request.getRequestURL());
            model.addAttribute("image","/assets/img/boba.jpg");
            model.addAttribute("des","Chào mừng bạn đến với Boba Shop");
            Cookie cookie = new Cookie("cart",null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return "home/payment-success";
        }
        return "pages-error-404";

    }
    @GetMapping("/thanh-toan/that-bai")
    public String orderError(@RequestParam("guid") Integer guid, Model model,HttpServletRequest request) throws PayPalRESTException {
        if(guid == 0) return "home/payment-error";
        Optional<Order> order = orderService.findById(guid);
        if(order.isPresent() ){
            order.get().setPaymentStatus(1);
            orderService.save(order.get());
            model.addAttribute("order",order.get());
            model.addAttribute("title","Thanh toán thất bại");
            model.addAttribute("curURL",request.getRequestURL());
            model.addAttribute("image","/assets/img/boba.jpg");
            model.addAttribute("des","Chào mừng bạn đến với Boba Shop");
            return "home/payment-error";
        }
        return "pages-error-404";
    }
    @GetMapping("/thanh-toan/vnpay")
    public String paymentVNPay(@RequestParam("vnp_ResponseCode") String vnp_ResponseCode, @RequestParam("vnp_TxnRef") Integer vnp_TxnRef,Model model,HttpServletRequest request,HttpServletResponse response){
        Optional<Order> order = orderService.findById(vnp_TxnRef);
        if(vnp_ResponseCode.equals("00")){
            if(order.isPresent()){
                model.addAttribute("order",order.get());
                model.addAttribute("title","Thanh toán thành công");
                model.addAttribute("curURL",request.getRequestURL());
                model.addAttribute("image","/assets/img/boba.jpg");
                model.addAttribute("des","Chào mừng bạn đến với Boba Shop");
                Cookie cookie = new Cookie("cart",null);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                return "home/payment-success";
            }
        }else{
            if(order.isPresent()){
                model.addAttribute("order",order.get());
                model.addAttribute("title","Thanh toán thất bại");
                model.addAttribute("curURL",request.getRequestURL());
                model.addAttribute("image","/assets/img/boba.jpg");
                model.addAttribute("des","Chào mừng bạn đến với Boba Shop");
                return "home/payment-error";
            }
        }
        return "pages-error-404";
    }
    @GetMapping("/tin-tuc")
    public String blog(Model model,HttpServletRequest request){
        String rssFeedUrl = "https://vnexpress.net/rss/giai-tri.rss";
        List<RSSItem> items = channel14RSSReader.readRSSFeed(rssFeedUrl);
        model.addAttribute("items", items);
        model.addAttribute("title","Tin tức");
        model.addAttribute("curURL",request.getRequestURL());
        model.addAttribute("image","/assets/img/boba.jpg");
        model.addAttribute("des","Chào mừng bạn đến với Boba Shop");
        return "home/blog";
    }

    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("code") String code, Model model,HttpServletRequest request){
        boolean verify = userService.verifyUser(code);
        if(!verify){
            return "pages-error-404";
        }
        model.addAttribute("success",verify);
        model.addAttribute("title","Xác thực tài khoản");
        model.addAttribute("curURL",request.getRequestURL());
        model.addAttribute("image","/assets/img/boba.jpg");
        model.addAttribute("des","Chào mừng bạn đến với Boba Shop");
        return "home/verify";
    }
//    @GetMapping( value = "/robots.txt")
//    public String robot(HttpServletResponse response,Model model)  {
//        String value = "User-agent: Googlebot\n" +
//                "Disallow: /nogooglebot/\n" +
//                "\n" +
//                "User-agent: *\n" +
//                "Allow: /\n" +
//                "\n" +
//                "Sitemap: https://bobaecor.live/sitemap.xml";
////        Path path = null;
////        try {
////            path = Paths.get(Objects.requireNonNull(getClass().getResource("/static/robots.txt")).toURI());
////        } catch (URISyntaxException e) {
////            throw new RuntimeException(e);
////        }
////        String value = null;
////        try {
////            value = new String(Files.readAllBytes(path));
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//        model.addAttribute("value", value);
//        return "robot";
//    }
}
