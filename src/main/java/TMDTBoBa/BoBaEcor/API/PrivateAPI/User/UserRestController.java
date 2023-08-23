package TMDTBoBa.BoBaEcor.API.PrivateAPI.User;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.StoreResponse;
import TMDTBoBa.BoBaEcor.Models.User.User;
import TMDTBoBa.BoBaEcor.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/user/api")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    @PostMapping(value = "/update")
    public ResponseEntity<StoreResponse> updateProfile(@ModelAttribute("user") User user, @RequestParam("newPassword") Optional<String> newPassword, @RequestParam("confirmPassword") Optional<String> confirmPassword)
    {
        StoreResponse storeResponse = userService.editProfile(user,newPassword,confirmPassword);
        return ResponseEntity.status(HttpStatusCode.valueOf(storeResponse.getCode())).body(storeResponse);
    }
}
