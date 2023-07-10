package TMDT.BoBa.API.CustomeHttpRe.Auth.Login;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String jwtToken;
    private String refreshToken;
    private String userName;
    // 1 is success, 0 is wrong user or pass, 2 is account not enable
    private Integer status;
}
