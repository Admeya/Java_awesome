package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.services.impl.TestingServiceFromCsv;

@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);

        TestingServiceFromCsv testingService = context.getBean(TestingServiceFromCsv.class);
        testingService.launchTesting();
    }
}
