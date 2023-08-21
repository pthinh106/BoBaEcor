package TMDTBoBa.BoBaEcor.Models.User;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "table_users_roles")
public class UserHasRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ur_id")
    private Integer urId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id" )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="role_id" )
    private Role role;

    public UserHasRole() {
    }

    public UserHasRole(Integer urId, User user, Role role) {
        this.urId = urId;
        this.user = user;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserHasRole that = (UserHasRole) o;
        return getUrId() != null && Objects.equals(getUrId(), that.getUrId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
