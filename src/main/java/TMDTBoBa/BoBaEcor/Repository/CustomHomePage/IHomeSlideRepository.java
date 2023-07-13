package TMDTBoBa.BoBaEcor.Repository.CustomHomePage;

import TMDTBoBa.BoBaEcor.Models.CustomeHomePage.HomeBanner;
import TMDTBoBa.BoBaEcor.Models.CustomeHomePage.HomeSide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHomeSlideRepository extends JpaRepository<HomeSide,Integer> {
}
