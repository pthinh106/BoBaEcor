package TMDTBoBa.BoBaEcor.API.PublicAPI.SMSOtp;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp.OTPValidateRequest;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp.OtpStatus;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp.SMSOtpRequest;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.SMSOtp.SMSOtpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(path = "/api/v1/otp",produces="application/json")
@RequiredArgsConstructor
public class SMSOtpController {
    private final SMSService smsService;
    private final Path storageFolder = Paths.get("uploads");
    @PostMapping("/send-otp")
    public ResponseEntity<SMSOtpResponse> sendOTP(@RequestBody SMSOtpRequest smsOtpRequest){
        SMSOtpResponse smsOtpResponse = smsService.sendSMS(smsOtpRequest);
        return ResponseEntity.status(smsOtpResponse.getStatus() == OtpStatus.DELIVERED ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).body(smsOtpResponse);
    }
    @PostMapping("/validate-otp")
    public ResponseEntity<Boolean> validateOTP(@RequestBody OTPValidateRequest otpValidateRequest){
        return ResponseEntity.ok(smsService.validateOtp(otpValidateRequest));
    }
    @GetMapping("/files/{fileName:.+}")
    // /files/06a290064eb94a02a58bfeef36002483.png
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            UrlResource resource = new UrlResource(file.toUri());
            byte[] bytes = new byte[1024];
            if (resource.exists() || resource.isReadable()) {
                 bytes = StreamUtils.copyToByteArray(resource.getInputStream());
            }
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }

}
