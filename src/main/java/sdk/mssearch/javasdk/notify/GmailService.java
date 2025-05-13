package sdk.mssearch.javasdk.notify;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sdk.mssearch.javasdk.core.BaseService;
import sdk.mssearch.javasdk.exception.ExceptionNotifyInfo;
import sdk.mssearch.javasdk.logger.SdkLoggerFactory;
import sdk.mssearch.javasdk.template.TemplateConstraints;
import sdk.mssearch.javasdk.template.TemplateLoader;
import sdk.mssearch.javasdk.utility.JacksonUtils;

import java.util.Map;

@Service
public class GmailService extends BaseService {

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

    public void sendErrorNotifyEmailWithSync(String to, String from, ExceptionNotifyInfo notifyInfo) throws MessagingException {

        String context = getErrorNotifyContext(notifyInfo);
        String subject = String.format("[Error Alert] [%s] %s",
                notifyInfo.getCurrentTime(),
                notifyInfo.getMessage());

        sendEmailWithSync(to, from, subject, context);
    }

    @SneakyThrows
    private String getErrorNotifyContext(ExceptionNotifyInfo notifyInfo) {
        String template = TemplateLoader.loadEmailTemplate(TemplateConstraints.EXCEPTION_NOTIFY_MAIL_TEMPLATE);
        log.debug("get error notify email with template: {}", template);
        Map<String, Object> map = null;
        try {
            map = JacksonUtils.objectToMap(notifyInfo);
        } catch (IllegalAccessException e) {
            log.error("Jackson covert error!", e);
            throw e;
        }
        return TemplateLoader.renderTemplate(template, map);
    }

    //test
    public static void main(String[] args) {
        Exception e = new Exception("test exception");
        GmailService gmailService = new GmailService();
        String context = gmailService.getErrorNotifyContext(ExceptionNotifyInfo.from("test", e));
        Logger log = SdkLoggerFactory.getLogger(GmailService.class);
        log.info(context);

    }
}
