package com.parsebyregex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.parsebyregex.controller") if in different package than root
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
