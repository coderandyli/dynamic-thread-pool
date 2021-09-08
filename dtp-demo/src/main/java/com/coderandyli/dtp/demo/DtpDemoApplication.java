package com.coderandyli.dtp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.coderandyli.dtp.client",
        "com.coderandyli.dtp.core",
        "com.coderandyli.dtp.demo"
})
public class DtpDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtpDemoApplication.class, args);
    }
}
