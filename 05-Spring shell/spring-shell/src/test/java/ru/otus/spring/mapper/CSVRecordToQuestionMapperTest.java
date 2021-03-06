package ru.otus.spring.mapper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.spring.model.Question;

import java.io.IOException;
import java.util.List;

public class CSVRecordToQuestionMapperTest {

    private CSVRecordToQuestionMapper mapper;

    @Before
    public void setUp() {
        mapper = new CSVRecordToQuestionMapper();
    }

    @Test
    public void map() throws IOException {
        CSVRecord record = getRecordList().get(0);

        Question resultQuestion = mapper.map(record);

        Assert.assertEquals(1L, resultQuestion.getQuestionNumber());
        Assert.assertEquals("Which attributes has <constructor-arg>", resultQuestion.getQuestion());
        Assert.assertEquals("id", resultQuestion.getAnswerA());
        Assert.assertEquals("value", resultQuestion.getAnswerB());
        Assert.assertEquals("name", resultQuestion.getAnswerC());
        Assert.assertEquals("b,c", resultQuestion.getRightAnswer());
    }

    private List<CSVRecord> getRecordList() throws IOException {
        String csvStrings = "" +
                "Which attributes has <constructor-arg>;id;value;name;b,c\n" +
                "What kind of injection Spring has?;constructor;getter;setter;a,c";

        return CSVParser.parse(csvStrings, CSVFormat.DEFAULT.withDelimiter(';')).getRecords();
    }
}