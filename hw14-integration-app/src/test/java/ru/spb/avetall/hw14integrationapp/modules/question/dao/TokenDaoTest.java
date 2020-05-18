package ru.spb.avetall.hw14integrationapp.modules.question.dao;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.spb.avetall.hw14integrationapp.modules.question.domain.Token;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
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