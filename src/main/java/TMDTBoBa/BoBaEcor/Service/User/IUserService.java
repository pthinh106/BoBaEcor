package TMDTBoBa.BoBaEcor.Service.User;


import TMDTBoBa.BoBaEcor.Models.User.User;

public interface IUserService {
    User getUser();
    boolean signUp(User user);
    User editProfile(User userDetails, User User);
    boolean verifyUser(String verificationCode);
    boolean changePassword(String currentPassword, String newPassword, User user);
}
