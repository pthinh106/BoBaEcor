package TMDTBoBa.BoBaEcor.API.PrivateAPI.Admin;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Service.Admin.Product.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/private/page")
@RequiredArgsConstructor
public class PageRestController {

    private final ProductService productService;

    @PostMapping("/product/{page}")
    public ResponseEntity<StoreResponse> getProductPage(@PathVariable Integer page){
        if(page <= 0){
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(null);
        }
        StoreResponse productPageResponse = productService.findPage(page);
        return ResponseEntity.status(HttpStatus.SC_OK).body(productPageResponse);
    }


}
