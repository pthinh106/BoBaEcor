package TMDTBoBa.BoBaEcor.Service.User;


import org.springframework.security.core.userdetails.User;

public interface IUserService {
    User getUser();
    boolean signUp(User user);
    User editProfile(User userDetails, User User);
    boolean verifyUser(String verificationCode);
    boolean changePassword(String currentPassword, String newPassword, User user);
}
