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
import ru.spb.otus.libraryapp.controller.LibraryController;
import ru.spb.otus.libraryapp.dao.GenreDao;
import ru.spb.otus.libraryapp.dao.impl.mapper.GenreRowMapper;
import ru.spb.otus.libraryapp.domain.Genre;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.dao.support.DataAccessUtils.singleResult;

@JdbcTest
@Sql(scripts = "classpath:genres_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LibraryController.class))
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
    }

    @Test
    void deleteAll() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findGenresByBookId() {
    }
}