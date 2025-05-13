package sdk.mssearch.javasdk.notify;

import lombok.SneakyThrows;
import sdk.mssearch.javasdk.ApplicationContextHolder;
import sdk.mssearch.javasdk.JavaWebSdkConfig;
import sdk.mssearch.javasdk.exception.ExceptionNotifyInfo;


public class MailNotifier implements Notifier {

    @SneakyThrows
    @Override
    public void notifyError(ExceptionNotifyInfo notifyInfo) {
        GmailService gmailService = ApplicationContextHolder.getBean(GmailService.class);
        JavaWebSdkConfig config = ApplicationContextHolder.getBean(JavaWebSdkConfig.class);
        String to = config.getLogger().getMailNotify();
        String from = config.getGmail().getMailUsername();
        gmailService.sendErrorNotifyEmailWithSync(to, from , notifyInfo);
    }
}
