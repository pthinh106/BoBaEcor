package TMDTBoBa.BoBaEcor.Service.Admin.Category;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Category;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.Store.ICategoryRepository;
import TMDTBoBa.BoBaEcor.Repository.User.IUserRepository;
import TMDTBoBa.BoBaEcor.Utilities.Contains;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            return new StoreResponse(500,"Category name not empty!",null);
        }
        try {
            Optional<User> user = iUserRepository.findById(1);
            if(user.isPresent()){
                category.setCategoryId(4);
                category.setCategorySlug(Contains.convertToURL(category.getCategoryName()));
                category.setUserCreate(user.get());
                category.setUserUpdate(user.get());
                category.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
                category.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
                category.setUserUpdate(user.get());
//                iCategoryRepository.save(category);
                return new StoreResponse(200,"Addon Category " + category.getCategoryName() + " success",StoreResponse.returnCategory(category));
            }else{
                return new StoreResponse(500,"Addon Fail! Server Error. ",null);
            }

        }catch (Exception e){
            return new StoreResponse(500,"Addon Fail! Server Error. " + e.getMessage(),null);
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
