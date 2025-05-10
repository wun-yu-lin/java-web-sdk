package sdk.mssearch.javasdk.logger;

import lombok.SneakyThrows;
import sdk.mssearch.javasdk.ApplicationContextHolder;
import sdk.mssearch.javasdk.JavaWebSdkConfig;
import sdk.mssearch.javasdk.exception.ExceptionNotifyInfo;
import sdk.mssearch.javasdk.notify.GmailService;


public class MailLoggerAdaptor extends BaseLoggerAdapter {

    public MailLoggerAdaptor(String loggerName) {
        super(loggerName);
    }

    @SneakyThrows
    @Override
    public void notifyError(String message, Throwable throwable) {
        GmailService gmailService = ApplicationContextHolder.getBean(GmailService.class);
        JavaWebSdkConfig config = ApplicationContextHolder.getBean(JavaWebSdkConfig.class);
        String to = config.getLogger().getMailNotify();
        String from = config.getGmail().getMailUsername();
        gmailService.sendErrorNotifyEmailWithSync(to, from , ExceptionNotifyInfo.from(message, throwable));
    }
}
