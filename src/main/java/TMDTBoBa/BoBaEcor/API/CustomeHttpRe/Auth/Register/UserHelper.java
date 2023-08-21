package TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Auth.Register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHelper {
    private Integer code;
    private String message;
    private String email;
    private String phoneNumber;
}
