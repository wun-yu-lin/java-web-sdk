package sdk.mssearch.javasdk.elk;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import jakarta.annotation.PostConstruct;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import sdk.mssearch.javasdk.JavaWebSdkConfig;

import java.util.ServiceLoader;

@Configuration
@EnableConfigurationProperties(JavaWebSdkConfig.class)
@ConditionalOnProperty(prefix = "java-sdk.logger-elk", name = "enable-elk", havingValue = "true")
public class LoggerElkConfigLoader {

    @Autowired
    JavaWebSdkConfig javaWebSdkConfig;

    @PostConstruct
    public void configureLogback() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        LogstashTcpSocketAppender appender = new LogstashTcpSocketAppender();
        appender.setName("LOGSTASH");
        appender.setContext(context);
        appender.addDestination(javaWebSdkConfig.getLoggerElk().getLogstashHost() + ":" + javaWebSdkConfig.getLoggerElk().getLogstashPort());

        LogDataEncoder encoder = new LogDataEncoder();
        encoder.setContext(context);
        encoder.start();
        appender.setEncoder(encoder);
        appender.start();

        context.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(appender);

        //extend
        for (LogbackCustomizer customizer : ServiceLoader.load(LogbackCustomizer.class)) {
            customizer.customize(context);
        }
    }


}
