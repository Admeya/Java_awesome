package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.CsvHandler;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        CsvHandler csvHandler = context.getBean(CsvHandler.class);
        List<Question> questions = csvHandler.readResource();
        csvHandler.printQuestions(questions);
    }
}
