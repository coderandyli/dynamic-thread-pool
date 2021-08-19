package com.coerandyli.dynamic.thread.pool.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.coderandyli.dynamic.thread.pool.client",
        "com.coerandyli.dynamic.thread.pool.admin.controller",
        "com.coerandyli.dynamic.thread.pool.admin.test",
        "com.coderandyli.dynamic.thread.pool.client.monitor"
})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
