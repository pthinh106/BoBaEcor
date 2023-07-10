package TMDTBoBa.BoBaEcor.API.CustomeHttpRe.User;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@RequiredArgsConstructor
@Getter
@Setter
@Data
public class UserRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String phoneNumber;

    private Date birthDay;

    private String address;
    @NotNull
    private String password;
}
