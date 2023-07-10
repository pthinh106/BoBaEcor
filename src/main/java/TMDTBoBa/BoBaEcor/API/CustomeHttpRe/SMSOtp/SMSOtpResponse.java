package TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SMSOtpResponse {
    private String urlValidate;
    private OtpStatus status;
    private String message;
}
