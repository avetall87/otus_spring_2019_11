package ru.spb.otus.libraryapp.dao.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ru.spb.otus.libraryapp.controller.AuthorController;
import ru.spb.otus.libraryapp.dao.GenreDao;
import ru.spb.otus.libraryapp.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Sql(scripts = "classpath:genres_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthorController.class))
class GenreDaoImplTest {

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private TestEntityManager em;

    @Test
    void findById() {
        Genre genre = genreDao.findById(100L);
        SoftAssertions.assertSoftly(softAssertions -> softAssertions.assertThat(genre).isNotNull());
    }

    @Test
    void findAll() {
        Long result = (Long) em.getEntityManager()
                .createQuery("select count(g) from Genre g")
                .getSingleResult();

        assertTrue(result > 0);
    }

    @Test
    void create() {
        String testGenreName = "test genre";
        genreDao.create(Genre.builder().name(testGenreName).build());

        Genre genre = (Genre) em.getEntityManager()
                .createQuery("select g from Genre g where g.name = :name")
                .setParameter("name", testGenreName)
                .getSingleResult();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(genre).isNotNull();
            softAssertions.assertThat(genre.getName()).isEqualTo(testGenreName);
        });
    }

    @Test
    void update() {
        String newName = "new name";
        genreDao.update(Genre.builder().id(100L).name(newName).build());

        Genre genre = em.find(Genre.class, 100L);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(genre).isNotNull();
            softAssertions.assertThat(genre.getName()).isEqualTo(newName);
        });
    }

    @Test
    void deleteById() {
        long genreId = 100L;
        genreDao.deleteById(genreId);

        Long count = (Long) em.getEntityManager()
                .createQuery("select count(g) from Genre g where g.id = :id")
                .setParameter("id", 100L)
                .getSingleResult();

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