package TMDTBoBa.BoBaEcor.Service.Auth;

import TMDT.BoBa.API.CustomeHttpRe.Auth.Login.AuthenticationRequest;
import TMDT.BoBa.API.CustomeHttpRe.Auth.Login.AuthenticationResponse;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.User.IUserRepository;
import TMDTBoBa.BoBaEcor.Utilities.Contains;
import TMDTBoBa.BoBaEcor.Utilities.Validate;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    public final PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    private final JWTService jwtService;


    //Login
    public AuthenticationResponse authenticationResponse(AuthenticationRequest authenticationRequest){
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
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
            return AuthenticationResponse.builder().jwtToken(JWTToken).refreshToken(RefreshToken).userName(user.get().getUsername()).status(1).build();
        }
        return AuthenticationResponse.builder().jwtToken("").refreshToken("").userName("").status(0).build();
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
