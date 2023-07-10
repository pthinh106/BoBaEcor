package TMDTBoBa.BoBaEcor.Repository.User;

import TMDTBoBa.BoBaEcor.Models.User.UserHasRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IUserHasRoleRepository extends JpaRepository<UserHasRole,Long> {
    List<UserHasRole> findAllByUser(User user);
}
