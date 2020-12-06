package ru.otus.spring.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.CsvHandler;
import ru.otus.spring.services.TestingService;

import java.util.List;

/**
 * Тестирование по списку вопросов из csv файла
 */
@Service
@AllArgsConstructor
public class TestingServiceFromCsv implements TestingService {

    final CsvHandler csvHandler;

    @Override
    public void launchTesting() {
        List<Question> questions = csvHandler.readResource();
        csvHandler.printQuestions(questions);
    }
}
