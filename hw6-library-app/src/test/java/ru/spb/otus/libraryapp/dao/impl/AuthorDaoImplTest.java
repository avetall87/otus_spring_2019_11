package ru.spb.otus.libraryapp.dao.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ru.spb.otus.libraryapp.controller.AuthorController;
import ru.spb.otus.libraryapp.dao.AuthorDao;
import ru.spb.otus.libraryapp.dao.impl.mapper.AuthorRowMapper;
import ru.spb.otus.libraryapp.domain.Author;

import java.util.List;

import static org.springframework.dao.support.DataAccessUtils.singleResult;

@DataJpaTest
@Sql("classpath:authors_test_data.sql")
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthorController.class))
class AuthorDaoImplTest {

    private final long AUTHOR_ID = 100L;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    void findById() {
        Assertions.assertNotNull(authorDao.findById(AUTHOR_ID));
    }

    @Test
    void findAll() {
        Assertions.assertNotNull(authorDao.findAll());
    }

    @Test
    void create() {
        authorDao.create(Author.builder().firstName("123").lastName("456").patronymic("678").build());
        Author author = singleResult(jdbcTemplate.query("select * from authors where first_name = :first_name",
                new MapSqlParameterSource("first_name", "123"),
                new AuthorRowMapper()));
        Assertions.assertNotNull(author);
    }

    @Test
    void update() {
        String test1 = "test1";
        String test2 = "test2";
        String test3 = "test3";

        authorDao.update(
                Author.builder()
                        .id(AUTHOR_ID)
                        .firstName(test1)
                        .lastName(test2)
                        .patronymic(test3)
                        .build()
        );

        Author author = singleResult(jdbcTemplate.query("select * from authors where id = :id", new MapSqlParameterSource("id", AUTHOR_ID), new AuthorRowMapper()));

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(author).isNotNull();
            softAssertions.assertThat(author.getId()).isEqualTo(AUTHOR_ID);
            softAssertions.assertThat(author.getFirstName()).isEqualTo(test1);
            softAssertions.assertThat(author.getLastName()).isEqualTo(test2);
            softAssertions.assertThat(author.getPatronymic()).isEqualTo(test3);
        });
    }

    @Test
    void deleteById() {
        long DELETE_AUTHOR_ID = 101L;
        authorDao.deleteById(DELETE_AUTHOR_ID);
        Long count = jdbcTemplate.queryForObject("select count(0) from authors where id = :id", new MapSqlParameterSource("id", DELETE_AUTHOR_ID), Long.class);
        Assertions.assertEquals(0, count);
    }

    @Test
    void findAuthorsByBookId() {
        List<Author> authors = authorDao.findAuthorsByBookId(AUTHOR_ID);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(authors));
    }
}