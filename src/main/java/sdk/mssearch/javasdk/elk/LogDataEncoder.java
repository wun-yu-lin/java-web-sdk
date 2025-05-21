package sdk.mssearch.javasdk.elk;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.EncoderBase;
import sdk.mssearch.javasdk.ApplicationContextHolder;
import sdk.mssearch.javasdk.JavaWebSdkConfig;
import sdk.mssearch.javasdk.utility.JacksonUtils;

import java.nio.charset.StandardCharsets;

public class LogDataEncoder extends EncoderBase<ILoggingEvent> {
    @Override
    public byte[] headerBytes() {
        return new byte[0];
    }

    @Override
    public byte[] encode(ILoggingEvent event) {
        try {
            JavaWebSdkConfig config = ApplicationContextHolder.getBean(JavaWebSdkConfig.class);
            LogData data = new LogData();

            data.setLogLevel(event.getLevel().toString());
            data.setThreadName(event.getThreadName());
            data.setLoggerName(event.getLoggerName());
            data.setMessage(event.getMessage());
            data.setAppName(config.getServerName());

            String json = JacksonUtils.objectToJson(data) + System.lineSeparator(); // 加上換行
            return json.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            //by pass
        }
        return ("{\"error\":\"serialization failure\"}\n").getBytes();
    }

    @Override
    public byte[] footerBytes() {
        return new byte[0];
    }
}
