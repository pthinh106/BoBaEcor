package TMDTBoBa.BoBaEcor.Models.Store;

import TMDTBoBa.BoBaEcor.Models.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "table_order")
@RequiredArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_id")
    private int orderId;
    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

    @Column(name = "First_name", columnDefinition = "varchar(255) NOT NULL")
    private String firstName;

    @Column(name = "Last_name", columnDefinition = "varchar(255) NOT NULL")
    private String lastName;

    @Column(name = "Phone_number",columnDefinition = "varchar(11)")
    private String phoneNumber;

    @Column(name = "voucher",columnDefinition = "varchar(255) NULL")
    private String voucher;

    @Column(name = "Address",columnDefinition = "varchar(255)")
    private String address;
    @Column(name = "note",columnDefinition = "varchar(255)")
    private String note;

    @Column(name = "Total",columnDefinition = "INT(11) DEFAULT 0")
    private double total;

    @Column(name = "Total_Sale",columnDefinition = "INT(11) DEFAULT 0")
    private double totalSale;

    @Column(name = "Payment",columnDefinition = "varchar(255)")
    private String payment;

    @Column(name = "Payment_status",columnDefinition = "tinyint(1) default 0")
    private int paymentStatus;

    @Column(name = "status",columnDefinition = "tinyint(1) default 0")
    private int status;

    @Column(name = "Created",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp()
    private Timestamp created;

    @Column(name = "Updated",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp()
    private Timestamp updated;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private Set<ProductOrder> listProductOrder = new HashSet<>();
}
