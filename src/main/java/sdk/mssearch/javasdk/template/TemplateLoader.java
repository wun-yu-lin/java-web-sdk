package sdk.mssearch.javasdk.template;

import lombok.SneakyThrows;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import sdk.mssearch.javasdk.logger.SdkLoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TemplateLoader {

    private final static Logger log = SdkLoggerFactory.getLogger(TemplateLoader.class);


    @SneakyThrows
    public static String loadEmailTemplate(String templateName) {

        //autoclose
        try (InputStream is = new ClassPathResource("templates/" + templateName).getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Failed to load template {}", templateName, e);
            throw e;
        }
    }

    public static String renderTemplate(String template, Map<String, Object> values) {
        StringSubstitutor substitutor = new StringSubstitutor(values);
        return substitutor.replace(template);
    }


    //test
    public static void main(String[] args) {
        log.info(TemplateLoader.loadEmailTemplate(TemplateConstraints.EXCEPTION_NOTIFY_MAIL_TEMPLATE));
    }
}
