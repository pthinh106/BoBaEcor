package TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store;

import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import TMDTBoBa.BoBaEcor.Models.Store.Category;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Utilities.Contains;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @JsonIgnore
    private Object dataDetail;
    private Integer totalPage;
    private Integer paging;
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
    public static Object returnProduct(List<Product> productList) {
        List<Map<String,String>> results = new ArrayList<>();
        int index = 1;
        for(Product product : productList){
            Map<String,String> result = new HashMap<>();
            result.put("trClass",(product.getProductId() % 2 == 0 ? "even" : "odd"));
            result.put("index", Integer.toString(index));
            result.put("id", product.getProductId().toString());
            result.put("slug", product.getProductSlug());
            result.put("name",product.getProductName());
            result.put("status",(product.getStatus() == 1 ? "Activity" : "Disable"));
            result.put("category",product.getCategory().getCategoryName());
            result.put("brand",product.getBrand().getBrandName());
            result.put("thumbnail",product.getProductThumbnail());
            result.put("totalQuantity",Integer.toString(product.getProductDetails().size()));
            results.add(result);
            index++;
        }
        return results;
    }
}
