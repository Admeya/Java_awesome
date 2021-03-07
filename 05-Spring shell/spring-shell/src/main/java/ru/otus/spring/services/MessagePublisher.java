package ru.otus.spring.services;

import java.util.Locale;


public interface MessagePublisher {

    String getHelloMessage(String name);

    String getHintMessage();

    String getAnswerMessage();

    String getResultMessage(String... args);

    String getSuccessMessage();

    String getFailMessage();

    Locale getLocale();
}
