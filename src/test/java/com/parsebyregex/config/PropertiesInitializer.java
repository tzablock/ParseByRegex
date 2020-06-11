package com.parsebyregex.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        String testResourcePath = System.getProperty("user.dir") + "/src/test/resources";
        TestPropertyValues.of(String.format("repository.path = %s", testResourcePath))
                .applyTo(configurableApplicationContext.getEnvironment());
    }
}
