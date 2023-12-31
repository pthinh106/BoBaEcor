package TMDTBoBa.BoBaEcor.API.PrivateAPI.Admin;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import TMDTBoBa.BoBaEcor.Models.Store.Category;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Service.store.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.store.Category.CategoryService;
import TMDTBoBa.BoBaEcor.Service.store.Product.ProductService;
import TMDTBoBa.BoBaEcor.Utilities.Contains;
import com.paypal.base.codec.binary.Base64;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/v1/private/addon")
@RequiredArgsConstructor
public class AddonRestController {

    private final CategoryService categoryService;

    private final BrandService brandService;

    private final ProductService productService;

    @PostMapping(value = "/product", consumes = { "multipart/form-data"})
    public ResponseEntity<StoreResponse> addonProduct(@ModelAttribute("product") Product product, @RequestParam("files") MultipartFile[] multipartFiles,
                                                @RequestParam("tSize") Optional<String[]>  tSize, @RequestParam("tColor") Optional<String[]> tColor,@RequestParam("tCodeColor") Optional<String[]> tCodeColor, @RequestParam("tPrice")  Optional<Integer[]> tPrice,
                                                @RequestParam("tSale") Optional<Integer[]> tSale, @RequestParam("tInventory") Optional<Integer[]> tInventory, @RequestParam("tSolid") Optional<Integer[]> tSolid)
                                               {
                                                   if(product.getProductId() != null) ResponseEntity.status(HttpStatusCode.valueOf(500)).body(new StoreResponse(500,"Server Error!",null,null,0,0));
        StoreResponse productResult = productService.addonProduct(product,multipartFiles,tSize,tColor,tCodeColor,tPrice,tSale,tInventory,tSolid);
        return ResponseEntity.status(HttpStatusCode.valueOf(productResult.getCode())).body(productResult);
    }



    @PostMapping("/brand")
    public ResponseEntity<StoreResponse> addonBrand(@ModelAttribute("brand") Brand brand) {
        StoreResponse brandResult = brandService.addonBrand(brand);
        return ResponseEntity.status(HttpStatusCode.valueOf(brandResult.getCode())).body(brandResult);
    }

    @PostMapping("/category")
    public ResponseEntity<StoreResponse> addonBrand(@ModelAttribute("category") Category category){
        StoreResponse categoryResult = categoryService.addonCategory(category);
        return ResponseEntity.status(HttpStatusCode.valueOf(categoryResult.getCode())).body(categoryResult);
    }
}
