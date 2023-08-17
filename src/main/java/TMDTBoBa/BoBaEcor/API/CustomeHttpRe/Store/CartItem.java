package TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store;

import TMDTBoBa.BoBaEcor.Models.Store.ProductDetail;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    private ProductDetail productDetail;
    private Integer quantity;
    private Integer totalPriceItem;
}
