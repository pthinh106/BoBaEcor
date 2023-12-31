package TMDTBoBa.BoBaEcor.Controller;

import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.VNPay.VNPayService;
import TMDTBoBa.BoBaEcor.Service.Blog.Channel14RSSReader;
import TMDTBoBa.BoBaEcor.Service.User.UserService;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Order.OrderService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseController {
    protected final PaypalService paypalService;
    protected final ProductService productService;
    protected final CategoryService categoryService;
    protected final BrandService brandService;
    protected final Channel14RSSReader channel14RSSReader;
    protected final UserService userService;
    protected final VNPayService vnPayService;
    protected final OrderService orderService;
}
