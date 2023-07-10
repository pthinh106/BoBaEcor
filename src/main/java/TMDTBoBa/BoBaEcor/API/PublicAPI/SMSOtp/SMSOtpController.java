package TMDTBoBa.BoBaEcor.API.PublicAPI.SMSOtp;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp.OTPValidateRequest;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp.OtpStatus;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp.SMSOtpRequest;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp.SMSOtpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/otp")
@RequiredArgsConstructor
public class SMSOtpController {
    private final SMSService smsService;
    @PostMapping("/send-otp")
    public ResponseEntity<SMSOtpResponse> sendOTP(@RequestBody SMSOtpRequest smsOtpRequest){
        SMSOtpResponse smsOtpResponse = smsService.sendSMS(smsOtpRequest);
        return ResponseEntity.status(smsOtpResponse.getStatus() == OtpStatus.DELIVERED ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).body(smsOtpResponse);
    }
    @PostMapping("/validate-otp")
    public ResponseEntity<Boolean> validateOTP(@RequestBody OTPValidateRequest otpValidateRequest){
        return ResponseEntity.ok(smsService.validateOtp(otpValidateRequest));
    }
}
