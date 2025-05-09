package sdk.mssearch.javasdk.logger;



public class DiscordLoggerAdaptor extends BaseLoggerAdapter {

    protected DiscordLoggerAdaptor(String name) {
        super(name);
    }

    @Override
    public void notifyError(String message, Throwable throwable) {

    }
}
