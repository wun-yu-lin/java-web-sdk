package sdk.mssearch.javasdk.logger;

import lombok.experimental.Delegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseLoggerAdapter implements LoggerAdaptor {

    @Delegate
    private final Logger delegate;

    protected BaseLoggerAdapter(String loggerName) {
        this.delegate = LoggerFactory.getLogger(loggerName);
    }
}
