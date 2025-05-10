package sdk.mssearch.javasdk.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sdk.mssearch.javasdk.ApplicationContextHolder;
import sdk.mssearch.javasdk.JavaWebSdkConfig;


/**
 * Logger factory for Logger adapter
 */
public class SdkLoggerFactory {

    private final static Logger logger = LoggerFactory.getLogger(SdkLoggerFactory.class);


    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }


    public static synchronized Logger getLogger(String loggerName) {
        //local startUp
        if (!ApplicationContextHolder.isSpringStarted()) {
            return LoggerFactory.getLogger(loggerName);
        }

        JavaWebSdkConfig config = ApplicationContextHolder.getBean(JavaWebSdkConfig.class);

        JavaWebSdkConfig.LoggerConfig.NotifyService notifyService = config.getLogger().getNotifyService();

        switch (notifyService) {

            case DISCORD -> {
                logger.debug("Discord logger enabled, logger name is {}", loggerName);
                return new DiscordLoggerAdaptor(loggerName);
            }
            case MAIL -> {
                logger.debug("Mail logger enabled, logger name is {}", loggerName);
                return new MailLoggerAdaptor(loggerName);
            }
            case LOCAL -> {
                logger.debug("Local logger enabled, logger name is {}", loggerName);
                return LoggerFactory.getLogger(loggerName);
            }

            default -> throw new RuntimeException("Notify service not implemented yet");
        }
    }
}
