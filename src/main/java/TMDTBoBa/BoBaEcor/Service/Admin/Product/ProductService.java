package TMDTBoBa.BoBaEcor.Service.Admin.Product;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Repository.Store.IProductPageRepository;
import TMDTBoBa.BoBaEcor.Repository.Store.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final IProductRepository iProductRepository;
    private final IProductPageRepository iProductPageRepository;
    @Override
    public Optional<Product> findById(Integer productId) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByName(String productName) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findBySlug(String productSlug) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByStatus(Integer status) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return iProductRepository.findAll();
    }

    @Override
    public StoreResponse findPage(Integer page) {
        try {
            int pageSize = 10;
            Pageable pageable = PageRequest.of(page-1,pageSize);
            int totalPages = iProductPageRepository.findAll(pageable).getTotalPages();
            return new StoreResponse(200,"Get Product Page Success",StoreResponse.returnProduct(iProductPageRepository.findAll(pageable).getContent()),iProductPageRepository.findAll(pageable).getContent(),totalPages,page);
        }catch (Exception e){
            return new StoreResponse(501,"Server Error",null,null,null,page);
        }
    }

    @Override
    public Product addonProduct(Product product) {
        return null;
    }

    @Override
    public Product editProduct(Product product) {
        return null;
    }

    @Override
    public Product hiddenProduct(Integer productId) {
        return null;
    }

    @Override
    public Product removeProduct(Integer productId) {
        return null;
    }
}
