package TMDTBoBa.BoBaEcor.Controller.Home;

import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping(path = "/san-pham")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("/{slug}")
    public String getProductDetailsBySlung(@PathVariable String slug, Model model, HttpServletRequest request){
        Optional<Product> productOption = productService.findBySlug(slug);
        if(productOption.isPresent()){
            model.addAttribute("product", productOption.get());
            model.addAttribute("productFirstColor", productOption.get().getItemColor().iterator().next());
            model.addAttribute("productFirstSize", productOption.get().getItemSize().iterator().next());
            model.addAttribute("curURL",request.getRequestURL());
            model.addAttribute("baseUrl", ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString());
        }
        return "home/product-details";

    }
}
