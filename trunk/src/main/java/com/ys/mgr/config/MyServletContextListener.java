package com.ys.mgr.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * ServletContext监听器
 *
 *
 * Date: 2017/12/8 09:30
 */
@Slf4j
@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("servletContext initialized......");
        // 可执行一些字典初始化的操作
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("servletContext destroyed......");
    }
}
