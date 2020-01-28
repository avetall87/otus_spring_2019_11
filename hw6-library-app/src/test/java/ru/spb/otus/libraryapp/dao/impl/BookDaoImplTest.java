package ru.spb.otus.libraryapp.dao.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ru.spb.otus.libraryapp.controller.AuthorController;
import ru.spb.otus.libraryapp.dao.BookDao;
import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Genre;

import java.util.List;

import static java.util.Collections.singletonList;

@DataJpaTest
@Sql(scripts = "classpath:books_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthorController.class))
class BookDaoImplTest {

    private final long BASE_BOOK_ID = 100L;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private TestEntityManager em;

    @Test
    void findById() {
        Assertions.assertNotNull(bookDao.findById(BASE_BOOK_ID));
    }

    @Test
    void findAll() {
        List<Book> books = bookDao.findAll();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(books).isNotNull();
            softAssertions.assertThat(books.size()).isGreaterThan(0);
        });
    }

    @Test
    void create() {

        String GENRE_NAME = "Жанр";
        String CREATE_AUTHOR_NAME = "Иванов";
        Book book = Book.builder()
                .name("test book")
                .description("test description")
                .authors(singletonList(Author.builder()
                        .firstName(CREATE_AUTHOR_NAME)
                        .lastName("")
                        .patronymic("")
                        .build()))
                .genres(singletonList(Genre.builder()
                        .name(GENRE_NAME)
                        .build()))
                .build();

        bookDao.save(book);

        Long count = (Long) em.getEntityManager().createNativeQuery("select count(0) cnt from books b left join authors_books ab on b.id = ab.book_id" +
                " left join books_genres bg on b.id = bg.book_id " +
                " where b.id = :id")
                .setParameter("id", book.getId())
                .getSingleResult();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(count).isNotNull();
            softAssertions.assertThat(count).isGreaterThan(0);
        });
    }

    @Test
    void update() {
        String newName = "new name";
        String newDescription = "new description";

        bookDao.update(Book.builder()
                .id(BASE_BOOK_ID)
                .name(newName)
                .description(newDescription)
                .build());

        Book book = em.find(Book.class, BASE_BOOK_ID);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(book).isNotNull();
            softAssertions.assertThat(book.getName()).isEqualTo(newName);
            softAssertions.assertThat(book.getDescription()).isEqualTo(newDescription);
        });
    }

    @Test
    void deleteById() {
        long DELETE_BOOK_ID = this.BASE_BOOK_ID;
        bookDao.deleteById(DELETE_BOOK_ID);

        Long count = em.getEntityManager().createQuery("select count(b) from Book b where b.id = :id", Long.class)
                .setParameter("id", DELETE_BOOK_ID)
                .getSingleResult();

        Assertions.assertEquals(0, count);
    }

    @Test
    void findByAuthor() {
        long FIND_AUTHOR_ID = 110L;
        Assertions.assertNotNull(bookDao.findByAuthor(Author.builder()
                .id(FIND_AUTHOR_ID)
                .build()));


    }

    @Test
    void findByGenre() {
        long FIND_GENRE_ID = 110L;
        Assertions.assertNotNull(bookDao.findByGenre(Genre.builder()
                .id(FIND_GENRE_ID)
                .build()));
    }

    @Test
    void addAuthor() {
        long ADD_AUTHOR_1_ID = 130L;
        bookDao.addAuthor(BASE_BOOK_ID, Author.builder().id(ADD_AUTHOR_1_ID).build());

        long ADD_AUTHOR_2_ID = 140L;
        bookDao.addAuthor(BASE_BOOK_ID, Author.builder().id(ADD_AUTHOR_2_ID).build());

        Long count = (Long) em.getEntityManager().createNativeQuery("select count(0) from authors_books ab where ab.author_id IN (:author_id1, :author_id2)", Long.class)
                .setParameter("author_id1", ADD_AUTHOR_1_ID)
                .setParameter("author_id2", ADD_AUTHOR_2_ID)
                .getSingleResult();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(count).isNotNull();
            softAssertions.assertThat(count).isGreaterThan(0);
        });
    }

    @Test
    void addGenre() {
        long CREATE_GENRE_1_ID = 110L;
        bookDao.addGenre(BASE_BOOK_ID, Genre.builder().id(CREATE_GENRE_1_ID).build());

        long CREATE_GENRE_2_ID = 120L;
        bookDao.addGenre(BASE_BOOK_ID, Genre.builder().id(CREATE_GENRE_2_ID).build());

        Long count = (Long) em.getEntityManager().createNativeQuery("select count(0) from books_genres bg where bg.genre_id IN (:genre_id1, :genre_id2)", Long.class)
                .setParameter("genre_id1", CREATE_GENRE_1_ID)
                .setParameter("genre_id2", CREATE_GENRE_2_ID)
                .getSingleResult();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(count).isNotNull();
            softAssertions.assertThat(count).isGreaterThan(0);
        });
    }

    @Test
    void deleteAuthor() {
        long DELETE_AUTHOR_2_ID = 115L;
        long DELETE_AUTHOR_BOOK_ID = 101L;
        bookDao.deleteAuthor(DELETE_AUTHOR_BOOK_ID, Author.builder().id(DELETE_AUTHOR_2_ID).build());

        Long count = (Long) em.getEntityManager().createNativeQuery("select count(0) from authors_books ab where ab.author_id = :author_id", Long.class)
                .setParameter("author_id", DELETE_AUTHOR_2_ID)
                .getSingleResult();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(count).isNotNull();
            softAssertions.assertThat(count).isEqualTo(0);
        });
    }

    @Test
    void deleteGenre() {
        long DELETE_GENRE_ID = 115L;
        long DELETE_GENRE_BOOK_ID = 101L;
        bookDao.deleteGenre(DELETE_GENRE_BOOK_ID, Genre.builder().id(DELETE_GENRE_ID).build());

        Long count = (Long) em.getEntityManager().createNativeQuery("select count(0) from books_genres bg where bg.genre_id = :genre_id", Long.class)
                .setParameter("genre_id", DELETE_GENRE_ID)
                .getSingleResult();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(count).isNotNull();
            softAssertions.assertThat(count).isEqualTo(0);
        });
    }
}