package TMDTBoBa.BoBaEcor.API.PrivateAPI.Admin;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Service.store.Order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/private/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;
    @PostMapping("/success/{id}")
    public ResponseEntity<StoreResponse> successOrder(@PathVariable Integer id)
    {
        StoreResponse storeResponse = orderService.orderSuccess(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(storeResponse.getCode())).body(storeResponse);
    }
    @PostMapping("/delivered/{id}")
    public ResponseEntity<StoreResponse> delivered(@PathVariable Integer id)
    {
        StoreResponse storeResponse = orderService.delivered(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(storeResponse.getCode())).body(storeResponse);
    }
}
