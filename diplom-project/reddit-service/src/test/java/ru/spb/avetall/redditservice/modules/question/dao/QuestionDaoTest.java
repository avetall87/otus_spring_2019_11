package ru.spb.avetall.redditservice.modules.question.dao;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.spb.avetall.redditservice.modules.question.domain.Question;
import ru.spb.avetall.redditservice.modules.question.domain.Token;

import java.util.List;

@Testcontainers
@SpringBootTest
@Sql("classpath:question_test_data.sql")
class QuestionDaoTest {

    @Autowired
    private QuestionDao questionDao;

    @Before
    public void setUp() {
    }

    @Test
    void findByTokenName() {

        Token token = Token.builder().tokenName("test").build();

        List<Question> questions = questionDao.findByTokenName(token.getTokenName());

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(questions).isNotEmpty();
            softAssertions.assertThat(questions.size()).isEqualTo(1);
            softAssertions.assertThat(questions.get(0).getAuthorFullName()).isEqualTo("test_name");
            softAssertions.assertThat(questions.get(0).getTitle()).isEqualTo("title");
            softAssertions.assertThat(questions.get(0).getUrl()).isEqualTo("url");
            softAssertions.assertThat(questions.get(0).getToken()).isNotNull();
            softAssertions.assertThat(questions.get(0).getToken().getTokenName()).isEqualTo("test");
        });
    }

    @Test
    void checkQuestionCountByAuthorFullNameAndTitleAndUrlAndCreationDateFindOne() {
        Long count = questionDao.checkQuestionCountByAuthorFullNameAndTitleAndUrlAndCreationDate("test_name", "title", "url", null, Token.builder().id(1L).build());

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(count).isNotNull();
            softAssertions.assertThat(count).isEqualTo(1);
        });
    }

    @Test
    void checkQuestionCountByAuthorFullNameAndTitleAndUrlAndCreationDateFindZero() {
        Long count = questionDao.checkQuestionCountByAuthorFullNameAndTitleAndUrlAndCreationDate("new author name", "title", "url", null, Token.builder().id(1L).build());

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(count).isNotNull();
            softAssertions.assertThat(count).isEqualTo(0);
        });
    }
}