package TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store;

import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import TMDTBoBa.BoBaEcor.Models.Store.Category;
import TMDTBoBa.BoBaEcor.Utilities.Contains;
import lombok.*;

import java.util.HashMap;
import java.util.Map;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreResponse {
    private Integer code;
    private String message;
    private Object data;

}
