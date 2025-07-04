package sdk.mssearch.javasdk;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({ //
        ApplicationContextHolder.class,
})
@Configuration
@ConditionalOnClass(JavaWebSdkConfig.class)
@EnableConfigurationProperties(JavaWebSdkConfig.class)
@ComponentScan("sdk.mssearch.javasdk")
public class JavaWebSdkAutoConfig {
}
