package TMDTBoBa.BoBaEcor.Service.store.Category;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Category;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.Store.ICategoryRepository;
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
    @Transactional
    public StoreResponse addonCategory(Category category) throws RuntimeException {
        try {
            if (category.getCategoryName().isEmpty()){
                throw new RuntimeException("Category name not empty!");
            }
            if(iCategoryRepository.existsByCategorySlug(Contains.convertToURL(category.getCategoryName()))){
                throw new RuntimeException("Can't initialize url because it already exists");
            }
                Optional<User> user = iUserRepository.findById(1);
                if(user.isPresent()){
                    category.setCategorySlug(Contains.convertToURL(category.getCategoryName()));
                    category.setUserCreate(user.get());
                    category.setUserUpdate(user.get());
                    category.setParentName( iCategoryRepository.findById(category.getParentId()).isPresent() ? iCategoryRepository.findById(category.getParentId()).get().getParentName() : "");
                    if(category.getParentId() != 0 ) category.setHasParent(1);
                            else category.setHasParent(0);
                    iCategoryRepository.save(category);
                    return new StoreResponse(200,"Addon Category " + category.getCategoryName() + " success",StoreResponse.returnCategory(category),category,null,null);
                }else{
                    throw new RuntimeException("Can not get info User");
                }

        }catch (RuntimeException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
