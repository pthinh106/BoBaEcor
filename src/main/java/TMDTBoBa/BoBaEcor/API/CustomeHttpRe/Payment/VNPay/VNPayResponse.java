package TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Payment.VNPay;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class VNPayResponse {
    private String url;

    public VNPayResponse(String url) {
        this.url = url;
    }
}
