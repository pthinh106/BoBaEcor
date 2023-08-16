
package TMDTBoBa.BoBaEcor;

import TMDTBoBa.BoBaEcor.API.PublicAPI.SMSOtp.TwilioConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BoBaEcorApplication {
	@Autowired
	private TwilioConfig twilioConfig;

	@PostConstruct
	public void setup() {
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}

	public static void main(String[] args) {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", "dso1iauz6",
				"api_key", "159819661593611",
				"api_secret", "Q3i_8Ka1bdWhK1TybYHoyiZHy8A"));
		SingletonManager manager = new SingletonManager();
		manager.setCloudinary(cloudinary);
		manager.init();
		SpringApplication.run(BoBaEcorApplication.class, args);
	}
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
