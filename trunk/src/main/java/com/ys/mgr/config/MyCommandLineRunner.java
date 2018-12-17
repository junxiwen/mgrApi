package com.ys.mgr.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 如果需要在SpringApplication启动时运行一些特定的代码，可以实现ApplicationRunner或CommandLineRunner接口。
 * 这两个接口以同样方式工作，并有一个单独的run方法，在SpringApplication.run(…​)之前会调用这个run方法。
 * 如果有多个实现ApplicationRunner或CommandLineRunner接口的类，可以使用@Order注解指定顺序
 *
 *
 * Date: 2017/12/8 09:28
 */
@Slf4j
@Component
@Order(0)
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("MyCommandLineRunner.run args: {}", args == null ? "null" : Arrays.toString(args));
    }
}
