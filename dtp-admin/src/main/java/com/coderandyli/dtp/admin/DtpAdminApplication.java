package com.coderandyli.dtp.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.coderandyli.dtp.core",
        "com.coderandyli.dtp.monitor",
        "com.coderandyli.dtp.admin"
})
@MapperScan({"com.coderandyli.dtp.admin.mapper"})
public class DtpAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtpAdminApplication.class, args);
    }

}
