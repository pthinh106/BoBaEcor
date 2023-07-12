package TMDTBoBa.BoBaEcor.Models.CustomeHomePage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "table_home_banner")
@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class HomeBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private int slideID;
    @Column(name = "banner_img",columnDefinition = "Varchar(255) NULL")
    private String slideImg;
    @Column(name = "banner_sub",columnDefinition = "Varchar(255) NULL")
    private String slideSub;
    @Column(name = "banner_title",columnDefinition = "Varchar(255) NULL")
    private String slideTitle;
    @Column(name = "status",columnDefinition = "tinyint(1) default 1")
    private boolean status;
}
