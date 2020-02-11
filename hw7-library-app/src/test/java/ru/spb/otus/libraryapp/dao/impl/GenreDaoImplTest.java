package ru.spb.otus.libraryapp.dao.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ru.spb.otus.libraryapp.controller.AuthorController;
import ru.spb.otus.libraryapp.dao.GenreDao;
import ru.spb.otus.libraryapp.domain.Genre;

import java.util.List;

@DataJpaTest
@Sql(scripts = "classpath:genres_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthorController.class))
class GenreDaoImplTest {

    @Autowired
    private GenreDao genreDao;


    @Test
    void findById() {
        Genre genre = genreDao.findById(100L).orElseThrow();
        SoftAssertions.assertSoftly(softAssertions -> softAssertions.assertThat(genre).isNotNull());
    }

    @Test
    void findAll() {
        List<Genre> result = genreDao.findAll();

        SoftAssertions.assertSoftly(softAssertions -> softAssertions.assertThat(result).isNotEmpty());
    }

    @Test
    void create() {
        String testGenreName = "test genre";
        genreDao.save(Genre.builder().name(testGenreName).build());

        Genre genre = genreDao.findAll()
                .stream()
                .filter(item -> testGenreName.equalsIgnoreCase(item.getName()))
                .findAny()
                .orElse(null);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(genre).isNotNull();
            softAssertions.assertThat(genre.getName()).isEqualTo(testGenreName);
        });
    }

    @Test
    void update() {
        String newName = "new name";
        long id = 100L;
        genreDao.update(newName, id);

        Genre genre = genreDao.findById(id).orElseThrow();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(genre).isNotNull();
            softAssertions.assertThat(genre.getName()).isEqualTo(newName);
        });
    }

    @Test
    void deleteById() {
        long genreId = 100L;
        genreDao.deleteById(genreId);

        Genre genre = genreDao.findById(genreId).orElse(null);

        SoftAssertions.assertSoftly(softAssertions -> softAssertions.assertThat(genre).isNull());
    }
}