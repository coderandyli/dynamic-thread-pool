package com.coderandyli.dynamic.thread.pool.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.coderandyli.dynamic.thread.pool.client",
        "com.coderandyli.dynamic.thread.pool.core",
        "com.coderandyli.dynamic.thread.pool.demo"
})
public class DynamicThreadPoolDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicThreadPoolDemoApplication.class, args);
    }
}
