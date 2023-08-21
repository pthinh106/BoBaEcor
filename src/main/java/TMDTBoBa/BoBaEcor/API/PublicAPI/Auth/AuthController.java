package TMDTBoBa.BoBaEcor.API.PublicAPI.Auth;


import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Auth.Login.AuthenticationRequest;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Auth.Login.AuthenticationResponse;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Auth.Register.UserHelper;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Service.Auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
@RequestMapping(path = "/api/v1/auth",produces="application/json")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping( value = "/login")
    public ResponseEntity<AuthenticationResponse> login(@ModelAttribute("authen") AuthenticationRequest authenticationRequest, HttpServletResponse response,HttpServletRequest request){
        AuthenticationResponse authenticationResponse = authService.authenticationResponse(authenticationRequest,request);
        if(authenticationResponse.getStatus() == 1) response.setHeader("Authorization","Bearer " + authenticationResponse.getJwtToken());
        return ResponseEntity.ok().body(authenticationResponse);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody Map<String,String> refreshToken){
        return ResponseEntity.ok().body(authService.refreshToken(refreshToken.get("refreshToken")));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@ModelAttribute("user") User user){
        UserHelper userHelper = authService.register(user);
        return ResponseEntity.status(HttpStatusCode.valueOf(userHelper.getCode())).body(userHelper);
    }

}
