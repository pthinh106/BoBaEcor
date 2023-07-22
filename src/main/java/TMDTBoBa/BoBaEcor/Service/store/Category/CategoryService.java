package TMDTBoBa.BoBaEcor.Service.store.Category;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Category;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.Store.ICategoryRepository;
import TMDTBoBa.BoBaEcor.Repository.User.IUserRepository;
import TMDTBoBa.BoBaEcor.Utilities.Contains;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository iCategoryRepository;
    private final IUserRepository iUserRepository;

    @Override
    public Optional<Category> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Category> findByName(String categoryNane) {
        return Optional.empty();
    }

    @Override
    public Optional<Category> findByStatus(Integer status) {
        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        return iCategoryRepository.findAll();
    }

    @Override
    public StoreResponse addonCategory(Category category) {
        if (category.getCategoryName().isEmpty()){
            return new StoreResponse(500,"Category name not empty!",null,null,null,null);
        }
        try {
            Optional<User> user = iUserRepository.findById(1);
            if(user.isPresent()){
                category.setCategorySlug(Contains.convertToURL(category.getCategoryName()));
                category.setUserCreate(user.get());
                category.setUserUpdate(user.get());
                iCategoryRepository.save(category);
                return new StoreResponse(200,"Addon Category " + category.getCategoryName() + " success",StoreResponse.returnCategory(category),category,null,null);
            }else{
                return new StoreResponse(500,"Addon Fail! Server Error. ",null,null,null,null);
            }

        }catch (Exception e){
            return new StoreResponse(500,"Addon Fail! Server Error. " + e.getMessage(),null,null,null,null);
        }
    }

    @Override
    public StoreResponse editCategory(Category brand) {
        return null;
    }

    @Override
    public StoreResponse removeBrand(Category brand) {
        return null;
    }
}
