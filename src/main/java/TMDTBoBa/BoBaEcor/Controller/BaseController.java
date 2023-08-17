package TMDTBoBa.BoBaEcor.Controller;

import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseController {
    protected final PaypalService paypalService;
    protected final ProductService productService;
    protected final CategoryService categoryService;
    protected final BrandService brandService;
}
