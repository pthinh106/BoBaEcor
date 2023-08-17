package TMDTBoBa.BoBaEcor.Models.Store;

import TMDTBoBa.BoBaEcor.Models.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "table_categories")
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

    @Column(name = "parent_id",columnDefinition = "INT(11) DEFAULT 0")
    private Integer parentId;
    @Column(name = "parent_name", columnDefinition = "Varchar(255) NULL")
    private String parentName;

    @Column(name = "has_parent", columnDefinition = "tinyint(1) default 0")
    private Integer hasParent;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    @Column(name = "category_status", columnDefinition = "tinyint(1) default 1")
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

}
