package TMDTBoBa.BoBaEcor.Controller;


import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.User.UserResponse;
import TMDTBoBa.BoBaEcor.Models.Store.Product;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Repository.Store.IProductRepository;
import TMDTBoBa.BoBaEcor.Repository.User.IUserRepository;
import TMDTBoBa.BoBaEcor.Service.Auth.AuthService;
import TMDTBoBa.BoBaEcor.Utilities.Contains;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping(path = "/demo")
@RequiredArgsConstructor
public class domoController {
    private final IProductRepository iProductRepository;

    private final IUserRepository iUserRepository;
    private final AuthService authService;

    private AuthenticationProvider authenticationProvider;
    @GetMapping("/test")
    public ResponseEntity<User> get(@RequestHeader("Authorization") String token){

        Optional<User> user = authService.getUser(token);
        UserResponse userResponse;
        if(user.isPresent()){
            userResponse = new UserResponse(user.get().getUserId(), user.get().getFirstName(),user.get().getLastName(),
                    user.get().getEmail(), user.get().getPhoneNumber(), (user.get().getBirthDay() == null ? "" : Contains.formatDate((Timestamp) user.get().getBirthDay())) ,user.get().getAddress(),user.get().getAccountStatus(),
                    Contains.formatDate(user.get().getCreatedOn()),Contains.formatDate(user.get().getUpdatedOn()));
            Set<String> listRole = new HashSet<>();
            user.get().getListRole().forEach(role-> listRole.add(role.getRole().getRoleName()));
            userResponse.setListRole(listRole);
        }
        return ResponseEntity.ok(user.get());
    }
    @GetMapping("/user")
    public ResponseEntity<List<User>> get(){

        return ResponseEntity.ok(iUserRepository.findAll());
    }

    @PostMapping("/user")
    public ResponseEntity<List<Product>> getAll(){

        return  ResponseEntity.ok(iProductRepository.findAll());
    }

    @GetMapping("/images")
    public ResponseEntity<Object> getImages() {
            Gson gson = new Gson();
            List<String> listImg = new ArrayList<>();
            listImg.add("/images1");
            listImg.add("/images2");
            listImg.add("/images3");
            String data = new Gson().toJson(listImg);
            List<String> list = gson.fromJson(data, ArrayList.class);
            list.forEach(System.out::println);
        return ResponseEntity.ok(listImg);
    }

    ///are your ready 2
//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request) throws LoginException {
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("0905633610", "123");
//        token.setDetails(new WebAuthenticationDetails(request));
//        Authentication authentication = this.authenticationProvider.authenticate(token);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        SecurityContextLoginModule securityContextLoginModule = new SecurityContextLoginModule();
//        if(securityContextLoginModule.login()){
//            return "error";
//        }
//        return "success";
//    }
}
