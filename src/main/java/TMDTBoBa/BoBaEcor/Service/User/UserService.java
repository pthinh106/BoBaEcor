package TMDTBoBa.BoBaEcor.Service.User;

import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.User.IUserRepository;
import TMDTBoBa.BoBaEcor.Service.User.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByPhoneNumberOrEmail(username,username);
    }

    @Override
    public boolean signUp(User user) {
        return false;
    }

    @Override
    public User editProfile(User userDetails, User User) {
        return null;
    }

    @Override
    public boolean verifyUser(String verificationCode) {
        Optional<User> user = userRepository.findByVerificationCode(verificationCode);
        if(user.isPresent()){
            user.get().setVerificationCode("");
            user.get().setAccountStatus(1);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(String currentPassword, String newPassword, User user) {
        return false;
    }
}
