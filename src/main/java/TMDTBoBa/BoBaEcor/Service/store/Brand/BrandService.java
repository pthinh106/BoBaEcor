package TMDTBoBa.BoBaEcor.Service.store.Brand;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.Store.IBrandRepository;
import TMDTBoBa.BoBaEcor.Repository.User.IUserRepository;
import TMDTBoBa.BoBaEcor.Utilities.Contains;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {
    private final IBrandRepository iBrandRepository;
    private final IUserRepository iUserRepository;
    @Override
    public Optional<Brand> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Brand> findByName(String brandNane) {
        return Optional.empty();
    }

    @Override
    public Optional<Brand> findByStatus(Integer status) {
        return Optional.empty();
    }

    @Override
    public List<Brand> findAll() {
        return iBrandRepository.findAll();
    }

    @Override
    @Transactional
    public StoreResponse addonBrand(Brand brand) throws RuntimeException{
        try {
            if (brand.getBrandName().isEmpty()){
                throw new RuntimeException("Brand name not empty!");
            }
            if(iBrandRepository.existsByBrandSlug(Contains.convertToURL(brand.getBrandName()))){
                throw new RuntimeException("Can't initialize url because it already exists");
            }
                Optional<User> user = iUserRepository.findById(1);
                if(user.isPresent()){
                brand.setBrandSlug(Contains.convertToURL(brand.getBrandName()));
                brand.setUserCreate(user.get());
                brand.setUserUpdate(user.get());
                iBrandRepository.save(brand);
                return new StoreResponse(200,"Addon Brand " + brand.getBrandName() + " success",StoreResponse.returnBrand(brand),brand,null,null);
                }else{
                    throw new RuntimeException("Can not get info user");
                }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new StoreResponse(500,"Addon Fail! Server Error. "+ e.getMessage(),null,null,null,null);
        }
    }

    @Override
    public StoreResponse editName(Brand brand) {
        return null;
    }

    @Override
    public StoreResponse removeBrand(Brand brand) {
        return null;
    }
}
