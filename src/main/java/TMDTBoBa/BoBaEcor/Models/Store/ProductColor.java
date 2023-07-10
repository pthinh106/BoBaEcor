package TMDTBoBa.BoBaEcor.Models.Store;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "table_colors")
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Integer colorId;

    @Column(name = "color_name",columnDefinition = "Varchar(255) NOT NULL")
    private String colorName;
}
