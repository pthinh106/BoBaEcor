package TMDTBoBa.BoBaEcor.Models.Store;

import TMDTBoBa.BoBaEcor.Models.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Table(name = "table_product_topic")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_topic_id")
    private Integer productTopicId;

    @Column(name = "product_topic_name",columnDefinition = "Varchar(255) NOT NULL")
    private String productTopicName;

    @Column(name = "status",columnDefinition = "tinyint(1) default 1")
    private boolean status;

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
}
