package sdk.mssearch.javasdk.logger;

import org.slf4j.Logger;

/**
 * All class implement this interface, if class need logging
 */
public interface Loggable {

    default Logger getLogger() {
        return SdkLoggerFactory.getLogger(this.getClass());
    }
}
