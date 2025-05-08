package sdk.mssearch.javasdk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "JavaWebSDKConfig.Base")
@ComponentScan
@Data
public class JavaWebSDKBaseConfig {
    private String serverName;

    @Bean
    public TestClint testClint() {
        return TestClint.newInstance(serverName);
    }

}
