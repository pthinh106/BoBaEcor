package TMDT.BoBa.Service.User;

import TMDTBoBa.BoBaEcor.Repository.User.IUserRepository;
import TMDTBoBa.BoBaEcor.Service.User.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    @Override
    public User getUser() {
        return null;
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
        return false;
    }

    @Override
    public boolean changePassword(String currentPassword, String newPassword, User user) {
        return false;
    }
}
