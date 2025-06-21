package sdk.mssearch.javasdk.notify;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import sdk.mssearch.javasdk.ApplicationContextHolder;
import sdk.mssearch.javasdk.JavaWebSdkConfig;
import sdk.mssearch.javasdk.core.ApiRequester;
import sdk.mssearch.javasdk.exception.ExceptionNotifyInfo;
import sdk.mssearch.javasdk.thread.MessageDispatcher;

import java.util.ArrayList;
import java.util.List;

public class DiscordNotifier implements Notifier {

    @Override
    public void notifyError(ExceptionNotifyInfo notifyInfo) {

        JavaWebSdkConfig webSdkConfig = ApplicationContextHolder.getBean(JavaWebSdkConfig.class);
        if (webSdkConfig == null || webSdkConfig.getDiscord() == null) {
            return ; //no config, do nothing
        }

        DiscordPayload payload = DiscordPayload.build(notifyInfo);

        Runnable task = () -> {
            try {
                ApiRequester.execPost(webSdkConfig.getDiscord().getWebhookUrl(), payload, null, null, false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        MessageDispatcher messageDispatcher = ApplicationContextHolder.getBean(MessageDispatcher.class);
        messageDispatcher.publish(task);



    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @AllArgsConstructor
    public static class DiscordPayload {
        private List<Embed> embeds;

        @Data
        public static class Embed {
            private String title = "\uD83D\uDEA8 Error Notification";
            private String description;
            private Integer color = 16711680;
            private List<Field> fields;
        }

        @Data
        @AllArgsConstructor
        public static class Field {
            private String name;
            private String value;
        }

        private static DiscordPayload build(ExceptionNotifyInfo e) {
            List<Embed> embeds = new ArrayList<>();
            Embed embed = new Embed();
            embed.setDescription("Error Message: "+ e.getCurrentTime());
            List<Field> fields = new ArrayList<>();
            embeds.add(embed);
            embed.setFields(fields);
            fields.add(new Field("Application name", e.getServerName()));
            fields.add(new Field("Source IP", e.getServerIp()));
            fields.add(new Field("Message", e.getMessage()));
            fields.add(new Field("Logger name", e.getLoggerName()));
            fields.add(new Field("cause", e.getCause()));

            return new DiscordPayload(embeds);
        }
    }
}
