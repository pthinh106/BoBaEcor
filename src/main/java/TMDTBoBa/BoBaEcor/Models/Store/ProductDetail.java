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

    @ManyToOne
    @JoinColumn(name = "size_id")
    private ProductSize productSize;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private ProductColor productColor;

    @Column(name = "quantity_inventory", columnDefinition = "INT(11) DEFAULT 0")
    private Integer quantityInventory;

    @Column(name = "quantity_sold", columnDefinition = "INT(11) DEFAULT 0")
    private Integer quantitySold;

    @Column(name = "product_price", columnDefinition = "INT(11) NOT NULL DEFAULT 0")
    private Integer productPrice;

    @Column(name = "product_price_sale", columnDefinition = "INT(11) NOT NULL DEFAULT 0")
    private Integer productPriceSale;

    @Column(name = "product_sale_status",columnDefinition = "tinyint(1) default 1")
    private boolean status;
}
