package sdk.mssearch.javasdk;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Integer.MIN_VALUE)
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.context = applicationContext;
    }

    public static  <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
