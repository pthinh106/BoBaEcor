package TMDTBoBa.BoBaEcor.Models.Store;

import TMDTBoBa.BoBaEcor.Models.User.User;
import com.google.gson.Gson;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Entity
@Table(name = "table_products")
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(name = "product_code", columnDefinition = "Varchar(255) NOT NULL", unique = true)
    private String productCode;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "product_name", columnDefinition = "Varchar(255) NOT NULL", unique = true)
    private String productName;

    @Column(name = "product_slug", columnDefinition = "Varchar(255) NOT NULL", unique = true)
    private String productSlug;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    private Set<ProductDetail> productDetails;

    @Column(name = "product_thumbnail", columnDefinition = "Varchar(255)")
    private String productThumbnail;

    @Column(name = "product_images",columnDefinition = "Text NULL")
    private String productImages;

    @Column(name = "product_description",columnDefinition = "Text NULL")
    private String productDescription;

    @Column(name = "product_status", columnDefinition = "tinyint(1) default 1")
    private int status;

    @Column(name = "created_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp()
    private Timestamp createdOn;

    @Column(name = "updated_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp()
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "user_create_id")
    private User userCreate;

    @ManyToOne
    @JoinColumn(name = "user_update_id")
    private User userUpdate;

    public Integer getTotalQuantityColor(ProductColor productColor){
        AtomicReference<Integer> total = new AtomicReference<>(0);
        productDetails.forEach(productDetail -> {
            if (productDetail.getProductColor() == productColor){
                total.updateAndGet(v -> v + productDetail.getQuantityInventory());
            }
        });
        return total.get();
    }
    public Integer getTotalQuantitySize(ProductSize productSize){
        AtomicReference<Integer> total = new AtomicReference<>(0);
        productDetails.forEach(productDetail -> {
            if (productDetail.getProductSize() == productSize){
                total.updateAndGet(v -> v + productDetail.getQuantityInventory());
            }
        });
        return total.get();
    }
    public List<String> getListImages(){
        Gson gson = new Gson();
        String data = new Gson().toJson(productImages);
        return gson.fromJson(data, List.class);
    }
}
