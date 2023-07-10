package TMDTBoBa.BoBaEcor.Models.Store;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "table_sizes")
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    private Integer colorId;

    @Column(name = "size_name",columnDefinition = "Varchar(255) NOT NULL")
    private String colorName;
}
