package TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Payment.VNPay;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@RequiredArgsConstructor
public class VNPayRequest {
    Long amount;
    String bankCode;
}
