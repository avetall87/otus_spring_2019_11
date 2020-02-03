package ru.spb.otus.libraryapp.dao.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ru.spb.otus.libraryapp.controller.AuthorController;
import ru.spb.otus.libraryapp.dao.AuthorDao;
import ru.spb.otus.libraryapp.domain.Author;

import java.util.List;

@DataJpaTest
@Sql("classpath:authors_test_data.sql")
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthorController.class))
class AuthorDaoImplTest {

    private final long AUTHOR_ID = 100L;

    @Autowired
    private AuthorDao authorDao;

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
        authorDao.save(Author.builder().firstName("123").lastName("456").patronymic("678").build());

        Author author = authorDao.findAll()
                .stream()
                .filter(item -> "123".equalsIgnoreCase(item.getFirstName()))
                .findAny()
                .orElse(null);

        Assertions.assertNotNull(author);
    }

    @Test
    void update() {
        String test1 = "test1";
        String test2 = "test2";
        String test3 = "test3";

        authorDao.update(test1, test2, test3, AUTHOR_ID);

        Author author = authorDao.findById(AUTHOR_ID).orElseThrow();

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

        Author author = authorDao.findById(DELETE_AUTHOR_ID).orElse(null);

        Assertions.assertNull(author);
    }

    @Test
    void findAuthorsByBookId() {
        List<Author> authors = authorDao.findAuthorsByBookId(AUTHOR_ID);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(authors));
    }
}