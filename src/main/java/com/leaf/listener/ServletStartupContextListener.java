package com.leaf.listener;

import com.leaf.filter.ShortUrlBloomFilter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Slf4j
@WebListener
public class ServletStartupContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("=============容器启动===============");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("=============容器销毁===============");
    }
}
