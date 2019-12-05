package ru.otus.spring.dao.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.dao.QuestionReader;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.util.List;

@DisplayName("Класс CsvReader")
class CsvReaderTest {

    private static QuestionReader questionReader;
    private static final String PATH_TO_CSV = "csv/question.csv";

    @BeforeAll
    static void setUp() {
        questionReader = new CsvReader();
    }

    @Test
    @DisplayName("Данные из csv файла корректно считываются в доменнную модель Question")
    void read() {

        Question etalon = Question.builder()
                .question("Во что, согласно древнегреческим мифам, взгляд Медузы Горгоны превращал всё живое?")
                .answers(List.of(Answer.builder().answer("test").isCorrect(true).build(),
                        Answer.builder().answer("test2").isCorrect(false).build()))
                .build();


        List<Question> questions = questionReader.read(PATH_TO_CSV);
        SoftAssertions.assertSoftly((softAssertions) -> {
            softAssertions.assertThat(questions).isNotNull();
            softAssertions.assertThat(questions).isNotEmpty();
            softAssertions.assertThat(questions.size()).isEqualTo(1);
            softAssertions.assertThat(questions.get(0).getQuestion()).isEqualTo(etalon.getQuestion());
            softAssertions.assertThat(questions.get(0).getAnswers().size()).isEqualTo(2);
            softAssertions.assertThat(questions.get(0).getAnswers().get(0).getAnswer()).isEqualTo(etalon.getAnswers().get(0).getAnswer());
            softAssertions.assertThat(questions.get(0).getAnswers().get(0).isCorrect()).isEqualTo(etalon.getAnswers().get(0).isCorrect());
            softAssertions.assertThat(questions.get(0).getAnswers().get(1).getAnswer()).isEqualTo(etalon.getAnswers().get(1).getAnswer());
            softAssertions.assertThat(questions.get(0).getAnswers().get(1).isCorrect()).isEqualTo(etalon.getAnswers().get(1).isCorrect());
            softAssertions.assertAll();
        });

    }
}