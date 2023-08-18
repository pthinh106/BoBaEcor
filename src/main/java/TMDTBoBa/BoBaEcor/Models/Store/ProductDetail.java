package TMDTBoBa.BoBaEcor.Models.Store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "table_product_details")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_detail_id")
    private Integer productDetailId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "size",columnDefinition = "Varchar(255) NOT NULL")
    private String size;

    @Column(name = "color",columnDefinition = "Varchar(255) NOT NULL")
    private String color;

    @Column(name = "code_color",columnDefinition = "Varchar(255) NOT NULL")
    private String codeColor;

    @Column(name = "quantity_inventory", columnDefinition = "INT(11) DEFAULT 0")
    private Integer quantityInventory;

    @Column(name = "quantity_solid", columnDefinition = "INT(11) DEFAULT 0")
    private Integer quantitySolid;

    @Column(name = "product_price", columnDefinition = "INT(11) NOT NULL DEFAULT 0")
    private Integer productPrice;

    @Column(name = "product_price_sale", columnDefinition = "INT(11) DEFAULT 0")
    private Integer productPriceSale;

    @Column(name = "product_sale_status",columnDefinition = "tinyint(1) default 1")
    private Integer saleStatus;

    @Column(name = "product_status",columnDefinition = "tinyint(1) default 1")
    private Integer detailStatus;
}
