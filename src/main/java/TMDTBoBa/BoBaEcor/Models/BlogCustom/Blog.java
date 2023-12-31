package TMDTBoBa.BoBaEcor.Models.BlogCustom;

import TMDTBoBa.BoBaEcor.Models.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Table(name = "table_blog")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Integer blogId;

    @ManyToOne
    @JoinColumn(name = "blog")
    BlogTopic blogTopic;

    @Column(name = "blog_des",columnDefinition = "TEXT NOT NULL")
    private String blogDes;

    @Column(name = "blog_img",columnDefinition = "Varchar(255) NULL")
    private String blogImg;

    @Column(name = "blog_status", columnDefinition = "tinyint(1) default 1")
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
