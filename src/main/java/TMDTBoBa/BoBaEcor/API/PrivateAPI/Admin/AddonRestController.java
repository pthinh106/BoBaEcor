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

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/private/addon")
@RequiredArgsConstructor
public class AddonRestController {

    private final CategoryService categoryService;

    private final BrandService brandService;

    @PostMapping("/product")
    public ResponseEntity<Product> addonProduct(@ModelAttribute("product") Product product){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Product());
    }

    @PostMapping("/brand")
    public ResponseEntity<StoreResponse> addonBrand(@ModelAttribute("brand") Brand brand){
        StoreResponse categoryResult = brandService.addonBrand(brand);
        if(categoryResult.getData() == null){
            return ResponseEntity.status(HttpStatusCode.valueOf(categoryResult.getCode())).body(categoryResult);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(categoryResult.getCode())).body(categoryResult);
    }

    @PostMapping("/category")
    public ResponseEntity<StoreResponse> addonBrand(@ModelAttribute("category") Category category){
        StoreResponse categoryResult = categoryService.addonCategory(category);
        if(categoryResult.getData() == null){
            return ResponseEntity.status(HttpStatusCode.valueOf(categoryResult.getCode())).body(categoryResult);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(categoryResult.getCode())).body(categoryResult);
    }

//    @PostMapping("/collection")
//    public ResponseEntity<CollectionStore> addonBrand(@ModelAttribute("CollectionStore") CollectionStore collectionStore){
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CollectionStore());
//    }
}
