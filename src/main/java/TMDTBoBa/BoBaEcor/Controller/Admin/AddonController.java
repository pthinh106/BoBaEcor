package TMDTBoBa.BoBaEcor.Controller.Admin;

import TMDTBoBa.BoBaEcor.Models.Store.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/admin/addon")
public class AddonController {

    @GetMapping(path = "/product")
    public String addonProduct(Model model){
        model.addAttribute("product", new Product());
        return "admin/Store/addon_product";
    }

    @PostMapping(value = "/product")
    public String addonProduct(@ModelAttribute("product") Product product, @RequestParam("files") MultipartFile[] multipartFiles,
                                                @RequestParam("price") Integer price, @RequestParam("sale") Integer sale, @RequestParam("priceDetail") Integer priceDetail){

        System.out.println(product.getProductDescription());
        System.out.println(product.getProductName());
        System.out.println(multipartFiles.length);
        System.out.println(price);
        System.out.println(sale);
        System.out.println(priceDetail);
        return "admin/Store/addon_product";
    }
}
