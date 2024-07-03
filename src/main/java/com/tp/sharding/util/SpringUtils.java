package com.tp.sharding.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2023/02/14 18:24:04
 */
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public static Object getBean(String className) throws BeansException, IllegalArgumentException {
        if (className == null || className.length() <= 0) {
            throw new IllegalArgumentException("className为空");
        }

        String beanName = null;
        if (className.length() > 1) {
            beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
        } else {
            beanName = className.toLowerCase();
        }
        return applicationContext != null ? applicationContext.getBean(beanName) : null;
    }

}
