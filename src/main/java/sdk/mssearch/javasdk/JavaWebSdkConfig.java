package sdk.mssearch.javasdk;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties("java-sdk")
@Data
public class JavaWebSdkConfig {

    @Bean
    public TestClint testClint() {
        return TestClint.newInstance(serverName);
    }

    private String serverName;

    private LoggerConfig logger;

    private GmailConfig Gmail;

    @Data
    public static class LoggerConfig {

        private NotifyService notifyService = NotifyService.LOCAL;

        private Boolean enableNotify;

        private String mailNotify;

        @RequiredArgsConstructor
        public enum NotifyService {
            DISCORD("discord"),

            MAIL("mail"),

            LOCAL("local");

            @Getter
            private final String value;
        }
    }

    @Data
    public static class GmailConfig {
        private String smtpHost;
        private Integer smtpPort;
        private String protocol = "smtp";
        private String mailUsername;
        private String mailPassword;
        private String smtpPassword;
        private Boolean smtpAuth;
        private Boolean smtpStarttlsEnable;
        private Boolean smtpStarttlsRequired;
    }

    public static JavaWebSdkConfig getBean() {
        return ApplicationContextHolder.getBean(JavaWebSdkConfig.class);
    }

}
