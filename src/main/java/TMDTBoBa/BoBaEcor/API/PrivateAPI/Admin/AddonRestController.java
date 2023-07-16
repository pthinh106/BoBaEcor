package TMDTBoBa.BoBaEcor.API.PrivateAPI.Admin;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import TMDTBoBa.BoBaEcor.Models.Store.Category;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Service.Admin.Brand.BrandService;
import TMDTBoBa.BoBaEcor.Service.Admin.Category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "/api/v1/private/addon")
@RequiredArgsConstructor
public class AddonRestController {

    private final CategoryService categoryService;

    private final BrandService brandService;

    @PostMapping(value = "/product", consumes = { "multipart/form-data"})
    public ResponseEntity<Product> addonProduct(@ModelAttribute("product") Product product, @RequestParam("files") MultipartFile[] multipartFiles,
                                                @RequestParam("price") Integer price,@RequestParam("sale") Integer sale,@RequestParam("priceDetail") Integer priceDetail){
        System.out.println(product.getProductName());
        System.out.println(product.getProductDescription());
        System.out.println(multipartFiles.length);
        System.out.println(price);
        System.out.println(sale);
        System.out.println(priceDetail);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PostMapping("/brand")
    public ResponseEntity<StoreResponse> addonBrand(@ModelAttribute("brand") Brand brand) throws InterruptedException {
        StoreResponse brandResult = brandService.addonBrand(brand);
        return ResponseEntity.status(HttpStatusCode.valueOf(brandResult.getCode())).body(brandResult);
    }

    @PostMapping("/category")
    public ResponseEntity<StoreResponse> addonBrand(@ModelAttribute("category") Category category){
        StoreResponse categoryResult = categoryService.addonCategory(category);
        return ResponseEntity.status(HttpStatusCode.valueOf(categoryResult.getCode())).body(categoryResult);
    }

//    @PostMapping("/collection")
//    public ResponseEntity<CollectionStore> addonBrand(@ModelAttribute("CollectionStore") CollectionStore collectionStore){
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CollectionStore());
//    }
}
