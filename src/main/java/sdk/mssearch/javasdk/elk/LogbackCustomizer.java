package sdk.mssearch.javasdk.elk;


import ch.qos.logback.classic.LoggerContext;

/**
 * Callback interface for customizing the Logback {@link LoggerContext}.
 * <p>
 * Implement this interface in your application to perform custom configuration
 * such as adding appenders, modifying log levels, or enriching log context.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * @Component
 * public class MyLoggerCustomizer implements LogbackCustomizer {
 *     @Override
 *     public void customize(LoggerContext context) {
 *         // Your custom logic here
 *         context.getLogger(Logger.ROOT_LOGGER_NAME).setLevel(Level.ERROR);
 *     }
 * }
 * }
 * </pre>
 */
@FunctionalInterface
public interface LogbackCustomizer {
    void customize(LoggerContext context);
}
