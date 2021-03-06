package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.configuration.ApplicationProperties;
import ru.otus.spring.services.impl.TestingServiceFromCsv;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class BlackMagicApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BlackMagicApplication.class, args);
        TestingServiceFromCsv testingService = context.getBean(TestingServiceFromCsv.class);
        testingService.launchTesting();
    }
}