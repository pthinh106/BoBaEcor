package TMDTBoBa.BoBaEcor.Models.CustomeHomePage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "table_home_slide")
@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class HomeSide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slide_id")
    private int slideID;
    @Column(name = "slide_img",columnDefinition = "Varchar(255) NULL")
    private String slideImg;
    @Column(name = "slide_sub",columnDefinition = "Varchar(255) NULL")
    private String slideSub;
    @Column(name = "slide_title",columnDefinition = "Varchar(255) NULL")
    private String slideTitle;
    @Column(name = "status",columnDefinition = "tinyint(1) default 1")
    private boolean status;
}
