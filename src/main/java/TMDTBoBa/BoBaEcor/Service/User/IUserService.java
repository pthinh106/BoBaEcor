package TMDTBoBa.BoBaEcor.Service.User;


import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.User.User;

import java.util.Optional;

public interface IUserService {
    User getUser();
    User save(User user);
    Optional<User> findUserByUsername(String username);
    boolean signUp(User user);
    StoreResponse editProfile(User user,Optional<String> newPassword, Optional<String> confirmPassword);
    boolean verifyUser(String verificationCode);
    boolean changePassword(String currentPassword, String newPassword, User user);
}
