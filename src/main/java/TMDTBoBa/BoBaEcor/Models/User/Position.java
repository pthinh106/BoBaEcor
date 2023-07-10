package TMDTBoBa.BoBaEcor.Models.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@Table(name = "table_positions")
public class Position {
    @Id
    @Column(name = "position_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long positionId;

    @Column(name = "position_name", columnDefinition = "Varchar(255)")
    private String positionName;

    @OneToMany(mappedBy = "position")
    @JsonIgnore
    private Set<User> listUser = new HashSet<>();
}
