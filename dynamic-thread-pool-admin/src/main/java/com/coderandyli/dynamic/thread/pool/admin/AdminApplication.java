package com.coderandyli.dynamic.thread.pool.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.coderandyli.dynamic.thread.pool.client",
        "com.coderandyli.dynamic.thread.pool.monitor",
        "com.coderandyli.dynamic.thread.pool.admin",
})
@MapperScan({"com.coderandyli.dynamic.thread.pool.admin.mapper"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
