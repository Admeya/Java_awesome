package ru.otus.spring.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.CsvHandler;
import ru.otus.spring.services.TestingService;

import java.util.List;
import java.util.Scanner;

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
        int count = examineStudents(questions);
        checkExamine(count);
    }

    private int examineStudents(List<Question> questions) {
        int rightAnswers = 0;
        Scanner scanner = new Scanner(System.in);
        String fio = introduceAndGetFio(scanner);

        for (Question question : questions) {
            csvHandler.printQuestion(question);
            System.out.println("Your answer:");
            String answer = scanner.nextLine();
            System.out.println("\n");
            if (isAnswerRight(question.getRightAnswer(), answer)) {
                rightAnswers++;
            }
        }

        System.out.println(fio + ", your result: " + rightAnswers + " correct answers from " + questions.size());
        return rightAnswers;
    }

    private String introduceAndGetFio(Scanner scanner) {
        System.out.println("Hi, introduce yourself, please:");
        String fio = scanner.nextLine();
        System.out.println("Thanks, let's start testing");
        System.out.println("If you choose multiple answer options, please, separate with comma. \n " +
                "For example a,c\n");
        return fio;
    }

    private boolean isAnswerRight(String expectedResult, String currentResult) {
        return expectedResult.equals(currentResult.replace(" ", ""));
    }

    private boolean checkExamine(int countCorrectAnswers) {
        int passed = 3;
        boolean isPassed = countCorrectAnswers >= passed;
        if (isPassed) {
            System.out.println("Congratulations! You passed the exam");
        } else {
            System.out.println("Sorry, try again later");
        }
        return isPassed;
    }
}
