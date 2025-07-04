package sdk.mssearch.javasdk.elk;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DefaultLoggerCustomizer implements LogbackCustomizer{
    @Override
    public void customize(LoggerContext context) {
        context.getLogger(Logger.ROOT_LOGGER_NAME).setLevel(Level.ERROR);
    }
}
