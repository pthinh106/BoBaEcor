package TMDTBoBa.BoBaEcor.Repository.User;


import TMDTBoBa.BoBaEcor.Models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phone);
}
