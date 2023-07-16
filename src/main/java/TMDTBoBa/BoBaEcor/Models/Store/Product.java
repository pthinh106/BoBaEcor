package TMDTBoBa.BoBaEcor.Models.Store;

import TMDTBoBa.BoBaEcor.Models.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Entity
@Table(name = "table_products")
@Setter
@Getter
@Data
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

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "product_name", columnDefinition = "Varchar(255) NOT NULL", unique = true)
    private String productName;

    @Column(name = "product_slug", columnDefinition = "Varchar(255) NOT NULL", unique = true)
    private String productSlug;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    private Set<ProductDetail> productDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductImages> productImages;

    @Column(name = "product_thumbnail", columnDefinition = "Varchar(255) NOT NULL")
    private String productThumbnail;

    @Column(name = "product_description",columnDefinition = "Text NULL")
    private String productDescription;

    @Column(name = "product_short_description",columnDefinition = "Text NULL")
    private String productShortDes;

    @Column(name = "product_sale_status",columnDefinition = "tinyint(1) default 1")
    private Integer saleStatus;

    @Column(name = "product_status", columnDefinition = "tinyint(1) default 1")
    private Integer status;

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
    @JsonIgnore
    public Set<ProductColor> getAllProductColor(){
        Set<ProductColor> productColors = new HashSet<>();
        productDetails.forEach(productDetail -> productColors.add(productDetail.getProductColor()));
        return productColors;
    }
    @JsonIgnore
    public Set<ProductSize> getAllProductSize(){
        Set<ProductSize> productSizes = new HashSet<>();
        productDetails.forEach(productDetail -> productSizes.add(productDetail.getProductSize()));
        return productSizes;
    }


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
}
