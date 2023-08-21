package TMDTBoBa.BoBaEcor.Models.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "table_roles")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleID;

    @Column(name = "role_name", columnDefinition = "varchar(255)")
    @NotNull
    private String roleName;

    @Column(name = "role_status", columnDefinition = "tinyint(1) default 1")
    private Long roleStatus;

    @Column(name = "created_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp()
    private Timestamp createdOn;

    @Column(name = "updated_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp()
    private Timestamp updatedOn;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private Set<UserHasRole> listUser = new HashSet<>();

    public Role() {

    }

    public Role(Integer roleID, @NotNull String roleName, Long roleStatus, Timestamp createdOn, Timestamp updatedOn, Set<UserHasRole> listUser) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.roleStatus = roleStatus;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.listUser = listUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return getRoleID() != null && Objects.equals(getRoleID(), role.getRoleID());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
