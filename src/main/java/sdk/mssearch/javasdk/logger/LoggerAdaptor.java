package sdk.mssearch.javasdk.logger;


import org.slf4j.Logger;

public interface LoggerAdaptor extends Logger {

    void notifyError(String message, Throwable throwable);

}
