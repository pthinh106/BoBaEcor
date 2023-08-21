package TMDTBoBa.BoBaEcor.Repository.User;

import TMDTBoBa.BoBaEcor.Models.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Integer> {
}
