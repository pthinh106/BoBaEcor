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

@Table(name = "table_blog_topic")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class BlogTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_topic_id")
    private Integer blogTopicId;

    @Column(name = "blog_topic_name",columnDefinition = "Varchar(255) NOT NULL")
    private String blogTopicName;

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
