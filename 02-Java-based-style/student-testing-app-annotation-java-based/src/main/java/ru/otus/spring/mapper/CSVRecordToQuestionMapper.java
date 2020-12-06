package ru.otus.spring.mapper;

import org.apache.commons.csv.CSVRecord;
import ru.otus.spring.model.Question;

public class CSVRecordToQuestionMapper {

    public Question map(CSVRecord csvRecord) {
        long recordNumber = csvRecord.getRecordNumber();
        return Question.builder()
                .questionNumber(recordNumber)
                .question(csvRecord.get(0))
                .answerA(csvRecord.get(1))
                .answerB(csvRecord.get(2))
                .answerC(csvRecord.get(3))
                .rightAnswer(csvRecord.get(4))
                .build();
    }
}
