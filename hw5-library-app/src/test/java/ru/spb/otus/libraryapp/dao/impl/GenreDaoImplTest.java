package ru.spb.otus.libraryapp.dao.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ru.spb.otus.libraryapp.controller.AuthorController;
import ru.spb.otus.libraryapp.dao.GenreDao;
import ru.spb.otus.libraryapp.dao.impl.mapper.GenreRowMapper;
import ru.spb.otus.libraryapp.domain.Genre;

import java.util.List;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.dao.support.DataAccessUtils.singleResult;

@JdbcTest
@Sql(scripts = "classpath:genres_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthorController.class))
class GenreDaoImplTest {

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    void findById() {
        Genre genre = genreDao.findById(100L);
        SoftAssertions.assertSoftly(softAssertions -> softAssertions.assertThat(genre).isNotNull());
    }

    @Test
    void findAll() {
        assertTrue(jdbcTemplate.queryForObject("select count(0) from genres", emptyMap(),Long.class) > 0);
    }

    @Test
    void create() {
        String testGenreName = "test genre";
        genreDao.create(Genre.builder().name(testGenreName).build());

        Genre genre = singleResult(jdbcTemplate.query("select * from genres where name = :name", new MapSqlParameterSource("name", testGenreName), new GenreRowMapper()));

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(genre).isNotNull();
            softAssertions.assertThat(genre.getName()).isEqualTo(testGenreName);
        });
    }

    @Test
    void update() {

        String newName = "new name";
        genreDao.update(Genre.builder().id(100L).name(newName).build());

        Genre genre = singleResult(jdbcTemplate.query("select * from genres where id = :id", new MapSqlParameterSource("id", 100L), new GenreRowMapper()));

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(genre).isNotNull();
            softAssertions.assertThat(genre.getName()).isEqualTo(newName);
        });
    }

    @Test
    void deleteAll() {
        genreDao.deleteAll();

        Long count = jdbcTemplate.queryForObject("select count(0) from genres", emptyMap(), Long.class);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(count).isNotNull();
            softAssertions.assertThat(count).isEqualTo(0);
        });
    }

    @Test
    void deleteById() {
        long genreId = 100L;
        genreDao.deleteById(genreId);

        Long count = jdbcTemplate.queryForObject("select count(0) from genres where id = :id", new MapSqlParameterSource("id", genreId), Long.class);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(count).isNotNull();
            softAssertions.assertThat(count).isEqualTo(0);
        });
    }

    @Test
    void findGenresByBookId() {
        List<Genre> genresByBookId = genreDao.findGenresByBookId(100L);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(genresByBookId).isNotNull();
            softAssertions.assertThat(genresByBookId.size()).isGreaterThan(0);
        });
    }
}