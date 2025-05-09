package sdk.mssearch.javasdk.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sdk.mssearch.javasdk.ApplicationContextHolder;
import sdk.mssearch.javasdk.JavaWebSdkConfig;


/**
 * Logger factory for Logger adapter
 */
public class SdkLoggerFactory {


    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }


    public static synchronized Logger getLogger(String loggerName) {
        JavaWebSdkConfig config = ApplicationContextHolder.getBean(JavaWebSdkConfig.class);
        JavaWebSdkConfig.LoggerConfig.NotifyService notifyService = config.getLogger().getNotifyService();

        switch (notifyService) {

            case DISCORD -> {
                return new DiscordLoggerAdaptor(loggerName);
            }
            case MAIL -> {
                return new MailLoggerAdaptor(loggerName);
            }
            case LOCAL -> {
                return LoggerFactory.getLogger(loggerName);
            }

            default -> throw new RuntimeException("Notify service not implemented yet");
        }
    }
}
