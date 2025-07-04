package sdk.mssearch.javasdk.notify;

import sdk.mssearch.javasdk.ApplicationContextHolder;
import sdk.mssearch.javasdk.JavaWebSdkConfig;
import sdk.mssearch.javasdk.JavaWebSdkConfig.LoggerConfig.NotifyService;
import sdk.mssearch.javasdk.exception.ExceptionNotifyInfo;

import java.util.*;

public interface Notifier {

    void notifyError(ExceptionNotifyInfo notifyInfo);

    static Map<NotifyService, Notifier> mapAllNotifier() {
        Map<NotifyService, Notifier> map = new HashMap<>();
        map.put(NotifyService.LOCAL, new DefaultNotifier());
        map.put(NotifyService.MAIL, new MailNotifier());
        map.put(NotifyService.DISCORD, new DiscordNotifier());

        return map;
    }

}
