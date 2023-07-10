package TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SMSOtpRequest {
    private String username;
    private String phoneNumber;
}
