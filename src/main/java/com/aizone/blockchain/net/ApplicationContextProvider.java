package com.aizone.blockchain.net;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * 应用程序上下文管理器
 *  
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass) {
        return context.getBean(tClass);
    }

    public static void publishEvent(ApplicationEvent event) {
        context.publishEvent(event);
    }
}
