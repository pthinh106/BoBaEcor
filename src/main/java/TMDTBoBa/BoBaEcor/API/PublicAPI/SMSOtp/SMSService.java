package TMDTBoBa.BoBaEcor.API.PublicAPI.SMSOtp;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp.OTPValidateRequest;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp.OtpStatus;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp.SMSOtpRequest;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp.SMSOtpResponse;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class SMSService {

    private final TwilioConfig twilioConfig;

    Map<String, String> otpMap = new HashMap<>();


    public SMSOtpResponse sendSMS(SMSOtpRequest smsOtpRequest) {
        SMSOtpResponse smsOtpResponse = null;
        try {
            PhoneNumber to = new PhoneNumber("whatsapp:"+smsOtpRequest.getPhoneNumber());//to
            PhoneNumber from = new PhoneNumber("whatsapp:"+twilioConfig.getWhatsapp()); // from
            String otp = generateOTP();
            String otpMessage = "Your BoBaShop OTP is verification code is: " + otp;
            Message message = Message
                    .creator(to, from,
                            otpMessage)
                    .create();
            otpMap.put(smsOtpRequest.getUsername(), otp);
            smsOtpResponse = new SMSOtpResponse("http://localhost:8060/user/verification", OtpStatus.DELIVERED, otpMessage);
        } catch (Exception e) {
            e.printStackTrace();
            smsOtpResponse = new SMSOtpResponse("",OtpStatus.FAILED, e.getMessage());
        }
        return smsOtpResponse;
    }

    public Boolean validateOtp(OTPValidateRequest otpValidationRequest) {
        Set<String> keys = otpMap.keySet();
        String username = null;
        for(String key : keys)
            username = key;
        if (otpValidationRequest.getUsername().equals(username)) {
            otpMap.remove(username,otpValidationRequest.getOtpNumber());
            return true;
        } else {
            return false;
        }
    }

    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}


//try {
//
//        Verification verification = Verification.creator(
//        "VAbe735989628cd2e291eaa2f92c7e5d52", // this is your verification sid
//        "+84905633610", //this is your Twilio verified recipient phone number
//        "sms") // this is your channel type
//        .create();
//        return "success";
//
//        } catch (Exception e) {
//
//        return e.getMessage();
//        }
