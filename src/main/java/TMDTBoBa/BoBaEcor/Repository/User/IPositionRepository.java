package TMDTBoBa.BoBaEcor.Repository.User;

import TMDTBoBa.BoBaEcor.Models.User.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPositionRepository extends JpaRepository<Position,Long> {
}
