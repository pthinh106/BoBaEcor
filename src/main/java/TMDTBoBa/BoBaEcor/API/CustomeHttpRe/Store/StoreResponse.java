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
    public static Map<String,String> returnCategory(Category category){
        Map<String,String> result = new HashMap<>();
        result.put("trClass",(category.getCategoryId() % 2 == 0 ? "even" : "odd"));
        result.put("index",category.getCategoryId().toString() );
        result.put("name",category.getCategoryName());
        result.put("status",(category.getStatus() == 1 ? "Activity" : "Disable"));
        result.put("user",category.getUserUpdate().getFullName());
        result.put("slug",category.getCategorySlug());
        return result;

    }

    public static Object returnBrand(Brand brand) {
        Map<String,String> result = new HashMap<>();
        result.put("trClass",(brand.getBrandId() % 2 == 0 ? "even" : "odd"));
        result.put("index",brand.getBrandId().toString() );
        result.put("name",brand.getBrandName());
        result.put("status",(brand.getStatus() == 1 ? "Activity" : "Disable"));
        result.put("user",brand.getUserUpdate().getFullName());
        result.put("slug",brand.getBrandSlug());
        return result;
    }
}
