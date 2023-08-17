package TMDTBoBa.BoBaEcor.API.PublicAPI.Store;

import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.Controller.BaseController;
import TMDTBoBa.BoBaEcor.Models.Store.ProductDetail;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/product")
public class ProductRestController extends BaseController {

    public ProductRestController(PaypalService paypalService, ProductService productService, CategoryService categoryService, BrandService brandService) {
        super(paypalService, productService, categoryService, brandService);
    }
    @GetMapping("/detail/{id}/{color}")
    public ResponseEntity<ProductDetail> getProductDetail(@PathVariable("color") String color, @PathVariable("id") Integer id){
        Optional<ProductDetail> productDetail = productService.findByColor(color, id);
        return productDetail.map(detail -> ResponseEntity.ok().body(detail)).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
