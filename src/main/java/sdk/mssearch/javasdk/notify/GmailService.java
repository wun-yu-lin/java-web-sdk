package sdk.mssearch.javasdk.notify;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class GmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailWithSync(String to, String from, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(text, true); // Use this or above line.
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(from);
        javaMailSender.send(mimeMessage);
    }
}
