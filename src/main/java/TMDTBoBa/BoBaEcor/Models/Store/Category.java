package TMDTBoBa.BoBaEcor.Models.Store;

import TMDTBoBa.BoBaEcor.Models.User.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "table_categorys")
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name = "category_name", columnDefinition = "Varchar(255) NOT NULL",unique = true)
    private String categoryName;

    @Column(name = "category_slug", columnDefinition = "Varchar(255) NOT NULL",unique = true)
    private String categorySlug;

    @Column(name = "category_status", columnDefinition = "tinyint(1) default 1")
    private Integer status;

    @Column(name = "created_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp()
    private Timestamp createdOn;

    @Column(name = "updated_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp()
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "user_create_id",columnDefinition = "NULL")
    private User userCreate;

    @ManyToOne
    @JoinColumn(name = "user_update_id",columnDefinition = "NULL")
    private User userUpdate;

}
