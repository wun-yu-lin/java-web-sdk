package sdk.mssearch.javasdk.logger;

import lombok.experimental.Delegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Delegate
    private final Logger delegate;

    private final Map<NotifyService, Notifier> notifierMap;

    public LoggerAdapterImpl(String loggerName) {
        this.notifierMap = Notifier.mapAllNotifier();
        this.delegate = LoggerFactory.getLogger(loggerName);
    }

    @Override
    public void error(String s, Throwable throwable) {
        delegate.error(s, throwable);
        try {
            notifyError(s, throwable);
        } catch (Exception e) {
            delegate.error("notifyError error: ", e);
        }
    }

    @Override
    public void notifyError(String message, Throwable throwable) {
        List<Notifier> notifiers = getNotifiersBySetting();
        notifiers.forEach(notifier -> {
            notifier.notifyError(ExceptionNotifyInfo.from(message, throwable));
        });
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
