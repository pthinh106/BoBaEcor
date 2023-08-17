package TMDTBoBa.BoBaEcor.Repository.Store;

import TMDTBoBa.BoBaEcor.Models.Store.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IProductPageRepository extends PagingAndSortingRepository<Product,Integer> {

    Page<Product> findAllByStatus(Integer status,Pageable pageable);
}
