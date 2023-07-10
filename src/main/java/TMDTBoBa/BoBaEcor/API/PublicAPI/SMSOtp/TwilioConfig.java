package TMDTBoBa.BoBaEcor.API.PublicAPI.SMSOtp;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Data
public class TwilioConfig {
    @Value("${Twilio.accountSid}")
    private String accountSid;
    @Value("${Twilio.authToken}")
    private String authToken;
    @Value("${Twilio.phoneNumber}")
    private String phoneNumber;
    @Value("${Twilio.Whatsapp}")
    private String whatsapp;
}
