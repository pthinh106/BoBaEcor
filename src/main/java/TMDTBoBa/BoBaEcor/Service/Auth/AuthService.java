package TMDTBoBa.BoBaEcor.Service.Auth;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Auth.Login.AuthenticationRequest;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Auth.Login.AuthenticationResponse;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Auth.Register.UserHelper;
import TMDTBoBa.BoBaEcor.API.PublicAPI.SMSOtp.SMSService;
import TMDTBoBa.BoBaEcor.Models.User.Role;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Models.User.UserHasRole;
import TMDTBoBa.BoBaEcor.Repository.User.IRoleRepository;
import TMDTBoBa.BoBaEcor.Repository.User.IUserHasRoleRepository;
import TMDTBoBa.BoBaEcor.Repository.User.IUserRepository;
import TMDTBoBa.BoBaEcor.Utilities.Contains;
import TMDTBoBa.BoBaEcor.Utilities.SendMail;
import TMDTBoBa.BoBaEcor.Utilities.Validate;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final IRoleRepository iRoleRepository;
    private final IUserHasRoleRepository iUserHasRoleRepository;
    private final SMSService smsService;
    private final SendMail sendMail;


    public final PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    private final JWTService jwtService;


    //Login
    public AuthenticationResponse authenticationResponse(AuthenticationRequest authenticationRequest, HttpServletRequest request){
        Optional<User> user = Optional.empty();
        if(Validate.validateEmail(authenticationRequest.getUsername())){
            user = userRepository.findByEmail(authenticationRequest.getUsername());
        }else if (Validate.validatePhone(authenticationRequest.getUsername())){
            user = userRepository.findByPhoneNumber(authenticationRequest.getUsername());
        }
        if (user.isPresent() && passwordEncoder().matches(authenticationRequest.getPassword(),user.get().getPassword())){
            if(!user.get().isEnabled()){
                return AuthenticationResponse.builder().jwtToken("").refreshToken("").userName("").status(2).build();
            }
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.get().getListRole().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole().getRoleName())));
            var JWTToken =  jwtService.generationJWTToken(user.get(),authorities);
            var RefreshToken =  jwtService.generationRefreshToken(user.get());
            return AuthenticationResponse.builder().jwtToken(JWTToken).refreshToken(RefreshToken).userName(user.get().getUsername()).status(1).message("Đăng nhập thành công").build();
        }
        return AuthenticationResponse.builder().jwtToken("").refreshToken("").userName("").status(0).message("Đăng nhập thất bại").build();
    }
    public AuthenticationResponse refreshToken(String refreshToken){
        Optional<User> user = getUser(refreshToken);
        if(user.isPresent()){
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.get().getListRole().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole().getRoleName())));
            var JWTToken =  jwtService.generationJWTToken(user.get(),authorities);
            var RefreshToken =  jwtService.generationRefreshToken(user.get());
            return AuthenticationResponse.builder().jwtToken(JWTToken).refreshToken(RefreshToken).userName(user.get().getUsername()).status(1).build();
        }
        return AuthenticationResponse.builder().jwtToken("").refreshToken("").userName("").status(0).build();
    }

    //Register
    @Transactional
    public UserHelper register(User user){
        try {
            if(userRepository.existsByEmail(user.getEmail())){
                throw new RuntimeException("Email is already exists!");
            }
            if(userRepository.existsByPhoneNumber(user.getPhoneNumber())){
                throw new RuntimeException("Phone number is already exists!");
            }
            if(user.getFirstName() == null || user.getFirstName().isEmpty() || user.getLastName() == null || user.getLastName().isEmpty()){
                throw new RuntimeException("Your Name is required!");
            }
            user.setPassword(passwordEncoder().encode(user.getPassword()));
            user.setAccountStatus(0);
            user.setIsEmployee(0);
            user.setVerificationCode(new DecimalFormat("000000")
                    .format(new Random().nextInt(999999)));
            userRepository.save(user);
            UserHasRole userHasRole = new UserHasRole();
            Optional<Role> role =  iRoleRepository.findById(2);
            if(role.isPresent()){
                userHasRole.setRole(role.get());
                userHasRole.setUser(user);
                iUserHasRoleRepository.save(userHasRole);
            }else{
                throw new RuntimeException("Server Error!");
            }
            String content = "Dear [[name]],<br>"
                    + "Please click the link below to your verify account:<br>"
                    + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                    + "Thank you,<br>"
                    + "BoBaEcor.";
            sendMail.sendMail(user.getFullName(),user.getEmail(),"bobaecor.live/verify?code=" + user.getAccessToken(),content,"Please verify your registration");
            return new UserHelper(200,"Đăng kí thành công! Vui lòng kiểm tra email để kích hoạt tài khoản", user.getEmail(), user.getPhoneNumber());

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new UserHelper(500, e.getMessage(), user.getEmail(), user.getPhoneNumber());
        }

    }


    @Transactional
    public UserHelper forgetPassword(String username){
        try {
            if(userRepository.existsByEmail(username) || userRepository.existsByPhoneNumber(username)){
                User user = userRepository.findByEmail(username).get();
                user.setForgetToken(new DecimalFormat("000000")
                        .format(new Random().nextInt(999999)));
                userRepository.save(user);
                String content = "Dear [[name]],<br>"
                        + "Please click the link below to your forget password:<br>"
                        + "<h3><a href=\"[[URL]]\" target=\"_self\">FORGET</a></h3>"
                        + "Thank you,<br>"
                        + "BoBaEcor.";
                sendMail.sendMail(user.getFullName(),user.getEmail(),"bobaecor.live/forget?code=" + user.getForgetToken(),content,"Request your forget password");

                return new UserHelper(200,"Vui lòng kiểm tra email của bạn",user.getEmail(), user.getPhoneNumber());

            } else{
                throw new RuntimeException("Không tìm thấy tài khoản thích hợp!");
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new UserHelper(500, e.getMessage(), username, username);
        }

    }

    //helper
    public Optional<User> getUser(String token){
        String userName;
        try {
            String JWTToken = token.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256(Contains.SECRET_KEY.getBytes());
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(JWTToken);
            userName = decodedJWT.getSubject();
        }catch (Exception e){
            return Optional.empty();
        }

        return userRepository.findByEmail(userName);
    }


}
