package sdk.mssearch.javasdk.logger;


import org.slf4j.Logger;
import org.slf4j.Marker;

public interface LoggerAdapter extends Logger {

    void notifyError(String message, Throwable throwable);
    void error(String s, Throwable t);
    void error(String msg);
    void error(String format, Object arg);
    void error(String format, Object arg1, Object arg2);
    void error(String format, Object... arguments);
    void error(Marker marker, String msg);
    void error(Marker marker, String format, Object arg);
    void error(Marker marker, String format, Object arg1, Object arg2);
    void error(Marker marker, String format, Object... arguments);
    void error(Marker marker, String msg, Throwable t);
}
