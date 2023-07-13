package TMDTBoBa.BoBaEcor.Repository.BlogCustom;

import TMDTBoBa.BoBaEcor.Models.BlogCustom.BlogTopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBlogTopicRepository extends JpaRepository<BlogTopic,Integer> {
}
