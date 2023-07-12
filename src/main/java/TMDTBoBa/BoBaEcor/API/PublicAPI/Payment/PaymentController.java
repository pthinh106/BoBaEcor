package TMDTBoBa.BoBaEcor.API.PublicAPI.Payment;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Payment.VNPay.VNPayResponse;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal.PaypalService;
import TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.VNPay.VNPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

@RestController
@RequestMapping(path = "/api/v1/payment",produces="application/json")
@RequiredArgsConstructor
public class PaymentController {

    private final VNPayService vnPayService;
    private final PaypalService paypalService;


    @PostMapping("/vnpay")
    ResponseEntity<VNPayResponse> vnPay() throws UnsupportedEncodingException, UnknownHostException {
        return ResponseEntity.ok().body(vnPayService.createPayment(100000,""));
    }

    @PostMapping("/paypal")
    ResponseEntity<Object> payPal() {
        return ResponseEntity.ok().body(paypalService.createPaypal());
    }


}
