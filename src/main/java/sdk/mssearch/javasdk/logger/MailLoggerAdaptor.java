package sdk.mssearch.javasdk.logger;

import lombok.SneakyThrows;
import sdk.mssearch.javasdk.ApplicationContextHolder;
import sdk.mssearch.javasdk.JavaWebSdkConfig;
import sdk.mssearch.javasdk.notify.GmailService;

import java.util.Arrays;

public class MailLoggerAdaptor extends BaseLoggerAdapter {

    public MailLoggerAdaptor(String loggerName) {
        super(loggerName);
    }

    @Override
    public void error(String s, Throwable throwable) {
        super.error(s, throwable);
        notifyError(s, throwable);
    }

    @SneakyThrows
    @Override
    public void notifyError(String message, Throwable throwable) {
        GmailService gmailService = ApplicationContextHolder.getBean(GmailService.class);
        JavaWebSdkConfig config = ApplicationContextHolder.getBean(JavaWebSdkConfig.class);
        String to = config.getLogger().getMailNotify();
        String from = config.getGmail().getMailUsername();
        gmailService.sendEmailWithSync(to, from , message, Arrays.toString(throwable.getStackTrace()));
    }
}
