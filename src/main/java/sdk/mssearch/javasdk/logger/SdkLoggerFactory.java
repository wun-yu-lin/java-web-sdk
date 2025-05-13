package sdk.mssearch.javasdk.logger;


import org.slf4j.Logger;


/**
 * Logger factory for Logger adapter
 * get logger must flow
 *     private final Logger log = SdkLoggerFactory.getLogger(TestController.class);
 */
public class SdkLoggerFactory {

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }


    public static synchronized Logger getLogger(String loggerName) {
        return new LoggerAdapterImpl(loggerName);
    }
}
