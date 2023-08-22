package TMDTBoBa.BoBaEcor.Models.Store;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "table_product_order")
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_Order_Id")
    Integer productOrderId;
    @ManyToOne
    @JoinColumn(name = "Order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;
    @Column(name = "Quantity",columnDefinition = "INT(11) DEFAULT 0")
    private int quantity;
    @Column(name = "Price",columnDefinition = "INT(11) DEFAULT 0")
    private double price;
}
