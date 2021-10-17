package ru.otus.spring.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.CsvHandler;
import ru.otus.spring.services.MessagePublisher;
import ru.otus.spring.services.TestingService;

import java.util.List;
import java.util.Scanner;

/**
 * Тестирование по списку вопросов из csv файла
 */
@Service
@AllArgsConstructor
public class TestingServiceFromCsv implements TestingService {

    private final CsvHandler csvHandler;
    private final MessagePublisher messagePublisher;

    @Override
    public int launchTesting() {
        List<Question> questions = csvHandler.readResource(messagePublisher.getLocale());
        return examineStudents(questions);
    }

    private int examineStudents(List<Question> questions) {
        int rightAnswers = 0;
        Scanner scanner = new Scanner(System.in);

        for (Question question : questions) {
            csvHandler.printQuestion(question);
            System.out.println(messagePublisher.getAnswerMessage());
            String answer = scanner.nextLine();
            System.out.println("\n");
            if (isAnswerRight(question.getRightAnswer(), answer)) {
                rightAnswers++;
            }
        }
        return rightAnswers;
    }

    private boolean isAnswerRight(String expectedResult, String currentResult) {
        return expectedResult.equals(currentResult.replace(" ", ""));
    }

    public void checkExamine(String fio, int countCorrectAnswers) {
        int passed = 3;
        boolean isPassed = countCorrectAnswers >= passed;

        System.out.println(messagePublisher.getResultMessage(fio, String.valueOf(countCorrectAnswers)));

        if (isPassed) {
            System.out.println(messagePublisher.getSuccessMessage());
        } else {
            System.out.println(messagePublisher.getFailMessage());
        }
    }
}
