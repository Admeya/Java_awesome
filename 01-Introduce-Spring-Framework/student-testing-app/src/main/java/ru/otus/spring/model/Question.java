package ru.otus.spring.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Question {

    private final long questionNumber;
    private final String question;
    private final String answerA;
    private final String answerB;
    private final String answerC;
    private final String rightAnswer;
}
