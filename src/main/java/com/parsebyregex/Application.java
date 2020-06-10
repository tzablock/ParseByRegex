package com.parsebyregex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@ComponentScan("com.parsebyregex.controller") if in different package than root
@PropertySource("classpath:dev-repository.properties")//TODO add dynamic classpath:${RUN_ENV}-repository.properties
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
