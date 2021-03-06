package ru.otus.spring.services;

/**
 * Сервис тестирования
 */
public interface TestingService {

    /**
     * Старт тестирования
     */
    int launchTesting();

    /**
     * Результат тестирования
     * @param fio имя пользователя
     * @param countCorrectAnswers количество правильных ответов
     */
    void checkExamine(String fio, int countCorrectAnswers);

}
