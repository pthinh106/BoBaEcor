package TMDTBoBa.BoBaEcor.Models.Store;

import TMDTBoBa.BoBaEcor.Models.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

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

    @Column(name = "no_type_status", columnDefinition = "tinyint(1) default 0")
    private Integer noTypeStatus;

    @Column(name = "quantity_inventory", columnDefinition = "INT(11) DEFAULT 0")
    private Integer quantityInventory;

    @Column(name = "quantity_solid", columnDefinition = "INT(11) DEFAULT 0")
    private Integer quantitySolid;

    @Column(name = "product_price", columnDefinition = "INT(11) DEFAULT 0")
    private Integer productPrice;

    @Column(name = "product_price_sale", columnDefinition = "INT(11) DEFAULT 0")
    private Integer productPriceSale;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    private Set<ProductDetail> productDetails = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductImages> productImages = new HashSet<>();

    @Column(name = "product_thumbnail", columnDefinition = "Varchar(255) NULL", unique = true)
    private String productThumbnail;

    @Column(name = "product_description",columnDefinition = "Text NULL")
    private String productDescription;

    @Column(name = "product_short_description",columnDefinition = "Text NULL")
    private String productShortDes;

    @Column(name = "product_sale_status",columnDefinition = "tinyint(1) default 0")
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
    public LinkedHashSet<Map<String,String>> getItemColor(){
        LinkedHashSet<Map<String,String>> item = new LinkedHashSet<>();
        productDetails.forEach(productDetail -> {
            AtomicBoolean exist = new AtomicBoolean(false);
            Map<String,String> data = new HashMap<>();
            data.put("color",productDetail.getColor());
            data.put("code",productDetail.getCodeColor());
            item.forEach(stringStringMap -> {
                if(stringStringMap.get("color").equals(data.get("color"))){
                    exist.set(true);
                }
            });
            if(!exist.get()) item.add(data);
        });
        return item;
    }

    @JsonIgnore
    public LinkedHashSet<String> getItemSize(){
        LinkedHashSet<String> item = new LinkedHashSet<>();
        productDetails.forEach(productDetail -> item.add(productDetail.getSize()));
        return item;
    }

}
