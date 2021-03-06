package ru.otus.spring.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.CsvHandler;
import ru.otus.spring.services.MessageHandler;
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
    private final MessageHandler localizationHandler;

    @Override
    public void launchTesting() {
        List<Question> questions = csvHandler.readResource(localizationHandler.getLocale());
        int count = examineStudents(questions);
        checkExamine(count);
    }

    private int examineStudents(List<Question> questions) {
        int rightAnswers = 0;
        Scanner scanner = new Scanner(System.in);
        String fio = introduceAndGetFio(scanner);

        for (Question question : questions) {
            csvHandler.printQuestion(question);
            System.out.println(localizationHandler.getAnswerMessage());
            String answer = scanner.nextLine();
            System.out.println("\n");
            if (isAnswerRight(question.getRightAnswer(), answer)) {
                rightAnswers++;
            }
        }

        System.out.println(localizationHandler.getResultMessage(fio, String.valueOf(rightAnswers),
                String.valueOf(questions.size())));
        return rightAnswers;
    }

    private String introduceAndGetFio(Scanner scanner) {
        System.out.println(localizationHandler.getIntroduceMessage());
        String fio = scanner.nextLine();
        System.out.println(localizationHandler.getHelloMessage(fio));
        System.out.println(localizationHandler.getHintMessage());
        return fio;
    }

    private boolean isAnswerRight(String expectedResult, String currentResult) {
        return expectedResult.equals(currentResult.replace(" ", ""));
    }

    private boolean checkExamine(int countCorrectAnswers) {
        int passed = 3;
        boolean isPassed = countCorrectAnswers >= passed;
        if (isPassed) {
            System.out.println(localizationHandler.getSuccessMessage());
        } else {
            System.out.println(localizationHandler.getFailMessage());
        }
        return isPassed;
    }
}
