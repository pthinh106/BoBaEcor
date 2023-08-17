package TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store;

import lombok.*;

import java.util.List;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart  {
    List<CartItem> cartItems;
    Integer totalPrice;
}
