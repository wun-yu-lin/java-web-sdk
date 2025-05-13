package sdk.mssearch.javasdk.logger;


import org.slf4j.Logger;

public interface LoggerAdapter extends Logger {

    void notifyError(String message, Throwable throwable);

    void error(String s, Throwable throwable);

}
