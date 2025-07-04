package sdk.mssearch.javasdk.elk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class LogData {
    @JsonProperty("@timestamp")
    private String currTime = OffsetDateTime.now().toString();

    @JsonProperty("message")
    private String message;

    @JsonProperty("logger_name")
    private String loggerName;

    @JsonProperty("thread_name")
    private String threadName;

    @JsonProperty("log_level")
    private String logLevel;

    @JsonProperty("application_name")
    private String appName;
}
