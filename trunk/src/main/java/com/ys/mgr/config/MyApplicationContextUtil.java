package com.ys.mgr.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 应用程序上下文工具
 * <p>
 *
 * Date: 2017/12/8 09:20
 */
@Component
public class MyApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

//    /**
//     * 入口函数中设置 ApplicationContext 进来
//     * @param context ApplicationContext
//     */
//    public static void setApplicationContext(ApplicationContext context) {
//        applicationContext = context;
//    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (MyApplicationContextUtil.applicationContext == null) {
            MyApplicationContextUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }

    public static <T> T getBean(Class<T> clazz, Object... args) {
        return getApplicationContext().getBean(clazz, args);
    }
}
