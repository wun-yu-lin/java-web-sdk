package sdk.mssearch.javasdk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import sdk.mssearch.javasdk.JavaWebSdkConfig;

import java.util.Properties;

@Configuration
public class JavaMailSenderConfig {

    @Autowired
    JavaWebSdkConfig javaWebSdkConfig;

    @Bean
    @Primary
    public JavaMailSender javaMailSender() {

        JavaWebSdkConfig.GmailConfig config = javaWebSdkConfig.getGmail();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(config.getSmtpHost());
        mailSender.setPort(config.getSmtpPort());
        mailSender.setUsername(config.getMailUsername());
        mailSender.setPassword(config.getMailPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", config.getProtocol());
        props.put("mail.smtp.auth", config.getSmtpAuth());
        props.put("mail.smtp.starttls.enable", config.getSmtpStarttlsEnable());

        return mailSender;
    }
}
