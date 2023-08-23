package TMDTBoBa.BoBaEcor.Service.User;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.User.IUserRepository;
import TMDTBoBa.BoBaEcor.Service.User.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public User getUser() {
        return null;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByPhoneNumberOrEmail(username,username);
    }

    @Override
    public boolean signUp(User user) {
        return false;
    }

    @Transactional
    @Override
    public StoreResponse editProfile(User user, Optional<String> newPassword, Optional<String> confirmPassword) {
        try {
            if(user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getAddress().isEmpty() || user.getPhoneNumber().isEmpty() || user.getEmail().isEmpty())
            {
                throw new RuntimeException("Fist name, last name, email, phone number, address are required!");
            }
            Optional<User> user1 = userRepository.findById(user.getUserId());
            if(user1.isPresent()){
                user1.get().setFirstName(user.getFirstName());
                user1.get().setLastName(user.getLastName());
                user1.get().setAddress(user.getAddress());
                user1.get().setEmail(user.getEmail());
                user1.get().setPhoneNumber(user.getPhoneNumber());
                if(newPassword.isPresent() && confirmPassword.isPresent()){
                    if(!passwordEncoder().matches(user.getPassword(),user1.get().getPassword())) throw new RuntimeException("Password not confirm!");
                    if(newPassword.get().equals(confirmPassword.get())){
                        user1.get().setPassword(passwordEncoder().encode(newPassword.get()));
                    }else{
                        throw new RuntimeException("new password and confirm password not same!");
                    }
                }
                userRepository.save(user1.get());
                return new StoreResponse(200,"Thay đổi thành công!",true,true,0,0);
            }else {
                throw new RuntimeException("Server Error!");
            }

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new StoreResponse(500, e.getMessage(), true,true,0,0);
        }

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
