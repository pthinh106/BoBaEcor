package TMDTBoBa.BoBaEcor.Utilities;



import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;

@Component
public class SendMail {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendMail(String userName,String email, String url,String content, String subject) throws UnsupportedEncodingException, jakarta.mail.MessagingException {
        String fromAddress = "lifeleopardjava@gmail.com";
        String senderName = "Boba Ecor Shop";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(email);
        helper.setSubject(subject);
        content = content.replace("[[name]]", userName);

        content = content.replace("[[URL]]", url);

        helper.setText(content, true);
        javaMailSender.send(message);
    }
}
