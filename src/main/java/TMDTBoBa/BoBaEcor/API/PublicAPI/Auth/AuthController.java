package TMDTBoBa.BoBaEcor.API.PublicAPI.Auth;

import TMDT.BoBa.API.CustomeHttpRe.Auth.Login.AuthenticationRequest;
import TMDT.BoBa.API.CustomeHttpRe.Auth.Login.AuthenticationResponse;
import TMDTBoBa.BoBaEcor.Service.Auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/auth",produces="application/json")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok().body(authService.authenticationResponse(authenticationRequest));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody Map<String,String> refreshToken){
        return ResponseEntity.ok().body(authService.refreshToken(refreshToken.get("refreshToken")));
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody ){
//
//    }

}
