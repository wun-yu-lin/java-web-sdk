package sdk.mssearch.javasdk.exception;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import sdk.mssearch.javasdk.JavaWebSdkConfig;
import sdk.mssearch.javasdk.logger.SdkLoggerFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Builder
@Getter
public class ExceptionNotifyInfo {

    private static final Logger logger = SdkLoggerFactory.getLogger(ExceptionNotifyInfo.class);
    private String message;
    private String cause;
    private String loggerName;
    private Throwable exception;
    private String fullStackTrace;
    private String serverName;
    private String serverIp;
    private Date currentTime;



    public static ExceptionNotifyInfo from(String message, Throwable throwable, String loggerName) {
        JavaWebSdkConfig config = JavaWebSdkConfig.getBean();
        String hostAddress;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostAddress = "";
            logger.warn("error getting host address", e);
        }

        // 安全處理 throwable 為 null 的情況
        String fullStackTrace = StringUtils.EMPTY;
        String rootCause = StringUtils.EMPTY;
        if (throwable != null) {
            rootCause = String.valueOf(throwable.getCause());
            fullStackTrace = ExceptionUtils.getStackTrace(throwable);
        }

        String serverName = StringUtils.EMPTY;
        if (config != null) {
            serverName = config.getServerName();
        }

        return ExceptionNotifyInfo.builder() //
                .message(message) //
                .exception(throwable) //
                .loggerName(loggerName)
                .cause(rootCause) //
                .fullStackTrace(fullStackTrace) //
                .serverName(serverName)
                .currentTime(new Date())
                .serverIp(hostAddress)
        .build();
    }
}
