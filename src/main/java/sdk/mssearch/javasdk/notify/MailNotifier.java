package sdk.mssearch.javasdk.notify;

import jakarta.mail.MessagingException;
import lombok.SneakyThrows;
import sdk.mssearch.javasdk.ApplicationContextHolder;
import sdk.mssearch.javasdk.JavaWebSdkConfig;
import sdk.mssearch.javasdk.exception.ExceptionNotifyInfo;
import sdk.mssearch.javasdk.thread.MessageDispatcher;


public class MailNotifier implements Notifier {

    @SneakyThrows
    @Override
    public void notifyError(ExceptionNotifyInfo notifyInfo) {
        MessageDispatcher messageDispatcher = ApplicationContextHolder.getBean(MessageDispatcher.class);
        Runnable task = () -> {
            GmailService gmailService = ApplicationContextHolder.getBean(GmailService.class);
            JavaWebSdkConfig config = ApplicationContextHolder.getBean(JavaWebSdkConfig.class);
            String to = config.getLogger().getMailNotify();
            String from = config.getGmail().getMailUsername();
            try {
                gmailService.sendErrorNotifyEmailWithSync(to, from , notifyInfo);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        };
        messageDispatcher.publish(task);


    }
}
