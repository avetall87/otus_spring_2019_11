package ru.spb.avetall.redditservice.modules.question.dao;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.spb.avetall.redditservice.modules.question.domain.Token;


@Testcontainers
@SpringBootTest
@Sql("classpath:token_test_data.sql")
class TokenDaoTest {

    @Autowired
    private TokenDao tokenDao;

    @Test
    void findByTokenName() {
        Token tokenName = tokenDao.findByTokenName("test");

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(tokenName).isNotNull();
            softAssertions.assertThat(tokenName.getTokenName()).isEqualTo("test");
        });
    }
}