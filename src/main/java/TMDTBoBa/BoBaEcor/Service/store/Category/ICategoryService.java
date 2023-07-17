package TMDTBoBa.BoBaEcor.Service.store.Category;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    Optional<Category> findById(Integer id);
    Optional<Category> findByName(String categoryNane);
    Optional<Category> findByStatus(Integer status);
    List<Category> findAll();
    StoreResponse addonCategory(Category category);
    StoreResponse editCategory(Category brand);
    StoreResponse removeBrand(Category brand);
}
