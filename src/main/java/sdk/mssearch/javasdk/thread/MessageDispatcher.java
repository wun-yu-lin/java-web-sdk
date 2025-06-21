package sdk.mssearch.javasdk.thread;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import sdk.mssearch.javasdk.logger.SdkLoggerFactory;

import java.util.concurrent.*;

@Component
public class MessageDispatcher {

    private final Logger logger = SdkLoggerFactory.getLogger(MessageDispatcher.class);
    private ThreadPoolExecutor executor;
    private final static int MAX_QUEUE_SIZE = 1024;
    private final static int DEFAULT_THREAD_POOL_SIZE = 2;
    private final static int DEFAULT_THREAD_POOL_MAX = Runtime.getRuntime().availableProcessors();
    private final static TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    private final static int DEFAULT_KEEP_ALIVE_TIME = 60;
    private final static String THREAD_POOL_NAME = "sdk-message-dispatcher-worker";

    @PostConstruct
    void init() {
        this.executor = new ThreadPoolExecutor(DEFAULT_THREAD_POOL_SIZE,
                DEFAULT_THREAD_POOL_MAX,
                DEFAULT_KEEP_ALIVE_TIME,
                DEFAULT_TIME_UNIT,
                new ArrayBlockingQueue<>(MAX_QUEUE_SIZE),
                new NamedThreadFactory(THREAD_POOL_NAME)
        );
    }

    public void publish(Runnable runnable) {
        logger.info("Publishing {}", runnable);
        try {
            executor.execute(runnable);
        } catch (Exception e) {
            logger.warn("Failed to publish {}, queue size: {} ", runnable, executor.getQueue().size(), e);
        }

    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }

}
