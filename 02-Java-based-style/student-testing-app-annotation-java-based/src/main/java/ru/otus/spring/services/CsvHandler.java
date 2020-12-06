package ru.otus.spring.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.spring.mapper.CSVRecordToQuestionMapper;
import ru.otus.spring.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:application.properties")
public class CsvHandler {

    private final CSVRecordToQuestionMapper mapper;

    @Value("${resource.csv}")
    private Resource resourceQuestionsCsv;

    public CsvHandler(CSVRecordToQuestionMapper mapper) {
        this.mapper = mapper;
    }

    public List<Question> readResource() {
        List<Question> questions = new ArrayList<>();
        try (InputStreamReader inputStreamReader = new InputStreamReader(resourceQuestionsCsv.getInputStream())) {
            for (CSVRecord csvRecord : getRecordSettings(inputStreamReader)) {
                questions.add(mapper.map(csvRecord));
            }
        } catch (IOException e) {
            System.out.println("Something wrong: " + e);
        }
        return questions;
    }

    public void printQuestions(List<Question> questions) {
        for (Question question : questions) {
            System.out.println("Question No - " + question.getQuestionNumber());
            System.out.println("---------------");
            System.out.println("Question : " + question.getQuestion());
            System.out.println("answerA : " + question.getAnswerA());
            System.out.println("answerB : " + question.getAnswerB());
            System.out.println("answerC : " + question.getAnswerC());
            System.out.println("---------------\n\n");
        }
    }

    private Iterable<CSVRecord> getRecordSettings(InputStreamReader in) throws IOException {
        return CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withDelimiter(';')
                .parse(in);
    }
}
