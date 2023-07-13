package TMDTBoBa.BoBaEcor.Service.Admin.Brand;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Brand;
import TMDTBoBa.BoBaEcor.Utilities.Contains;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService implements IBrandService {
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
        return null;
    }

    @Override
    public StoreResponse addonBrand(Brand brand) {
        if (brand.getBrandName().isEmpty()){
            return new StoreResponse(500,"Brand name not empty!",null);
        }
        try {
            brand.setBrandSlug(Contains.convertToURL(brand.getBrandName()));
            return new StoreResponse(200,"Addon Brand " + brand.getBrandName() + " success",brand);
        }catch (Exception e){
            return new StoreResponse(500,"Addon Fail! Server Error. "+ e.getMessage(),null);
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
