package TMDTBoBa.BoBaEcor.Models.Store;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "table_product_images")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id",columnDefinition = "INT(11)")
    private Integer productImageId;

    @Column(name = "product_image",columnDefinition = "Varchar(255) DEFAULT NULL")
    private String productImage;

    @ManyToOne
    @JoinColumn(name = "product")
    Product product;

    @Column(name = "image_status",columnDefinition = "tinyint(1) default 1")
    private Integer status;
}
