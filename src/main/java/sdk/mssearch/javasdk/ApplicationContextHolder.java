package sdk.mssearch.javasdk;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Integer.MIN_VALUE)
@SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    private static volatile boolean inSpringStarted = false;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.context = applicationContext;
        inSpringStarted = true;
    }

    public static  <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    public static boolean isSpringStarted() {
        return inSpringStarted;
    }
}
