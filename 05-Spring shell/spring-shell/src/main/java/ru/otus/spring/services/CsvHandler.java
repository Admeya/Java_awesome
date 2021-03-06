package ru.otus.spring.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.spring.mapper.CSVRecordToQuestionMapper;
import ru.otus.spring.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class CsvHandler {

    private final CSVRecordToQuestionMapper mapper;

    @Value("${resource.csv.en}")
    private Resource resourceQuestionsEnCsv;

    @Value("${resource.csv.ru}")
    private Resource resourceQuestionsRuCsv;

    public CsvHandler(CSVRecordToQuestionMapper mapper) {
        this.mapper = mapper;
    }

    public List<Question> readResource(Locale locale) {
        List<Question> questions = List.of();
        try (InputStreamReader inputStreamReader = new InputStreamReader(getLocalizationFile(locale).getInputStream())) {
            questions = saveRecords(inputStreamReader);
        } catch (IOException e) {
            System.out.println("Something wrong: " + e);
        }
        return questions;
    }

    public void printQuestion(Question question) {
        System.out.println("---------------");
        System.out.println(question.getQuestionNumber() + ". " + question.getQuestion());
        System.out.println("a : " + question.getAnswerA());
        System.out.println("b : " + question.getAnswerB());
        System.out.println("c : " + question.getAnswerC());
        System.out.println("---------------");
    }

    private Resource getLocalizationFile(Locale locale) {
        if (Locale.ENGLISH.equals(locale)) {
            return resourceQuestionsEnCsv;
        }

        return resourceQuestionsRuCsv;
    }

    private List<Question> saveRecords(InputStreamReader inputStreamReader) throws IOException {
        List<Question> questions = new ArrayList<>();
        for (CSVRecord csvRecord : getRecordSettings(inputStreamReader)) {
            questions.add(mapper.map(csvRecord));
        }
        return questions;
    }

    private Iterable<CSVRecord> getRecordSettings(InputStreamReader in) throws IOException {
        return CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withDelimiter(';')
                .parse(in);
    }
}
