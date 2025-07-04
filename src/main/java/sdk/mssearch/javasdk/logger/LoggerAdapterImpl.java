package sdk.mssearch.javasdk.logger;

import lombok.experimental.Delegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.util.CollectionUtils;
import sdk.mssearch.javasdk.ApplicationContextHolder;
import sdk.mssearch.javasdk.JavaWebSdkConfig;
import sdk.mssearch.javasdk.JavaWebSdkConfig.LoggerConfig.NotifyService;
import sdk.mssearch.javasdk.exception.ExceptionNotifyInfo;
import sdk.mssearch.javasdk.notify.Notifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LoggerAdapterImpl implements LoggerAdapter {

    private final String loggerName;

    @Delegate
    private final Logger delegate;

    private final Map<NotifyService, Notifier> notifierMap;

    public LoggerAdapterImpl(String loggerName) {
        this.notifierMap = Notifier.mapAllNotifier();
        this.delegate = LoggerFactory.getLogger(loggerName);
        this.loggerName = loggerName;
    }

    @Override
    public void error(String s, Throwable throwable) {
        delegate.error(s, throwable);
        notifyError(s, throwable);
    }

    @Override
    public void error(String msg) {
        delegate.error(msg);
    }

    @Override
    public void error(String format, Object arg) {
        delegate.error(format, arg);
        notifyError(formatSafe(format, arg), null);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        delegate.error(format, arg1, arg2);
        notifyError(formatSafe(format, arg1, arg2), null);
    }

    @Override
    public void error(String format, Object... arguments) {
        delegate.error(format, arguments);
        notifyError(formatSafe(format, arguments), null);
    }

    @Override
    public void error(Marker marker, String msg) {
        delegate.error(marker, msg);
        notifyError(msg, null);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        delegate.error(marker, format, arg);
        notifyError(formatSafe(format, arg), null);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        delegate.error(marker, format, arg1, arg2);
        notifyError(formatSafe(format, arg1, arg2), null);
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        delegate.error(marker, format, arguments);
        notifyError(formatSafe(format, arguments), null);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        delegate.error(marker, msg, t);
        notifyError(msg, t);
    }


    @Override
    public void notifyError(String message, Throwable throwable) {
        try {
            ExceptionNotifyInfo info = ExceptionNotifyInfo.from(message, throwable, loggerName);

            //ELK full stacktrace
            delegate.error(info.getFullStackTrace(), throwable);

            //notifier
            List<Notifier> notifiers = getNotifiersBySetting();
            notifiers.forEach(notifier -> {
                notifier.notifyError(info);
            });
        } catch (Exception e) {
            delegate.error("notifyError error: ", e);
        }

    }

    private String formatSafe(String format, Object... args) {
        if (args == null || args.length == 0) return format;
        try {
            return String.format(format.replace("{}", "%s"), args);
        } catch (Exception e) {
            return format + " " + List.of(args);
        }
    }

    private List<Notifier> getNotifiersBySetting() {
        if (!ApplicationContextHolder.isSpringStarted()) {
            return Collections.singletonList(notifierMap.get(NotifyService.LOCAL));
        }

        JavaWebSdkConfig config = ApplicationContextHolder.getBean(JavaWebSdkConfig.class);
        List<NotifyService> configNotifyService = config.getLogger().getNotifyServiceList();

        if (CollectionUtils.isEmpty(configNotifyService)) {
            return Collections.singletonList(notifierMap.get(NotifyService.LOCAL));
        }
        List<Notifier> notifiers = new ArrayList<>();
        configNotifyService.forEach(notifyService -> {
            if (notifierMap.containsKey(notifyService)) {
                notifiers.add(notifierMap.get(notifyService));
            };
        });

        return notifiers;
    }
}
