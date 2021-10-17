package ru.otus.spring.mapper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.otus.spring.model.Question;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CSVRecordToQuestionMapperTest {

    @SpyBean
    private CSVRecordToQuestionMapper mapper = new CSVRecordToQuestionMapper();

    @Test
    public void map() throws IOException {
        CSVRecord record = getRecordList().get(0);

        Question resultQuestion = mapper.map(record);

        assertThat(resultQuestion.getQuestionNumber()).isEqualTo(1L);
        assertThat(resultQuestion.getQuestion()).isEqualTo("Which attributes has <constructor-arg>");
        assertThat(resultQuestion.getAnswerA()).isEqualTo("id");
        assertThat(resultQuestion.getAnswerB()).isEqualTo("value");
        assertThat(resultQuestion.getAnswerC()).isEqualTo("name");
        assertThat(resultQuestion.getRightAnswer()).isEqualTo("b,c");
    }

    private List<CSVRecord> getRecordList() throws IOException {
        String csvStrings = "" +
                "Which attributes has <constructor-arg>;id;value;name;b,c\n" +
                "What kind of injection Spring has?;constructor;getter;setter;a,c";

        return CSVParser.parse(csvStrings, CSVFormat.DEFAULT.withDelimiter(';')).getRecords();
    }
}