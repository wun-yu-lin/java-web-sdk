package sdk.mssearch.javasdk;

import org.springframework.context.annotation.Import;
import sdk.mssearch.javasdk.elk.LoggerElkConfigLoader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({JavaWebSdkAutoConfig.class, LoggerElkConfigLoader.class})
public @interface EnableWebSdk {
}
