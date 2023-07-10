package TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OTPValidateRequest {
    private String username;
    private String otpNumber;
}
