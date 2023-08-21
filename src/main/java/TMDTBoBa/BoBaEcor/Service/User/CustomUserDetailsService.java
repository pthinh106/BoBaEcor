package TMDTBoBa.BoBaEcor.Service.User;

import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.User.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(iUserRepository.existsByEmail(username) || iUserRepository.existsByPhoneNumber(username)){
            User user = iUserRepository.findByPhoneNumberOrEmail(username,username).get();
            if(user.isEnabled()){
                return user;
            }
            else return null;
        }
        return null;
    }
}
