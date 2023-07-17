package TMDTBoBa.BoBaEcor.Service.store.Brand;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Brand;

import java.util.List;
import java.util.Optional;

public interface IBrandService {
    Optional<Brand> findById(Integer id);
    Optional<Brand> findByName(String brandNane);
    Optional<Brand> findByStatus(Integer status);
    List<Brand> findAll();
    StoreResponse addonBrand(Brand brand);
    StoreResponse editName(Brand brand);
    StoreResponse removeBrand(Brand brand);
}
