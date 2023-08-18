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
    private String name;
    private String thumbnail;
    private Integer quantity;
    private Integer totalPriceItem;
    private String size;
}
