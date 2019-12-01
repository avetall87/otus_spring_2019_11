package ru.otus.spring.readers.impl;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Question;
import ru.otus.spring.readers.QuestionReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

    private static QuestionReader questionReader;
    private static final String PATH_TO_CSV = "csv/question.csv";

    @BeforeAll
    static void setUp() {
        questionReader = new CsvReader();
    }

    @Test
    void read() {
        List<Question> questions = questionReader.read(PATH_TO_CSV);
        SoftAssertions.assertSoftly((softAssertions)->{
            softAssertions.assertThat(questions).isNotNull();
            softAssertions.assertThat(questions).isNotEmpty();
            softAssertions.assertAll();
        });

    }
}