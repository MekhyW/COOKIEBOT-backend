package com.cookiebot.cookiebotbackend.utils;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;

public final class BeanUtils {

    public static <T> void registerBean(ApplicationContext context, Class<T> beanClass, T bean, String beanName) {
        var beanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();

        try {
            beanFactory.getBeanDefinition(beanName);
            return;
        } catch (NoSuchBeanDefinitionException ignored) {}

        var beanDefinition = new RootBeanDefinition(beanClass, () -> bean);
        beanDefinition.setPrimary(true);

        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}
