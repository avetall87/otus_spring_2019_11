package ru.spb.spring.libraryapp.dao.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.spb.spring.libraryapp.AbstractIntegrationTest;
import ru.spb.spring.libraryapp.domain.Author;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.events.exception.DeleteBookConstraintException;
import ru.spb.spring.libraryapp.repository.BookDao;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookDaoTest extends AbstractIntegrationTest {

    private static final String NEW_BOOK = "new book";

    @Autowired
    private BookDao bookDao;

    @Autowired
    private MongoOperations mongoOperations;

    @BeforeEach
    void setUp() {
        mongoOperations.dropCollection(Book.class);
    }

    @Test
    void findById() {

        Book book = Book.builder().name(NEW_BOOK).build();
        mongoOperations.save(book);

        Book result = bookDao.findById(book.getId()).orElse(null);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result).isNotNull();
            softAssertions.assertThat(result.getName()).isEqualTo(NEW_BOOK);

        });
    }

    @Test
    void findAll() {
        Book book = Book.builder().name(NEW_BOOK).build();
        mongoOperations.save(book);

        long count = bookDao.count();

        assertThat(count).isEqualTo(1);
    }

    @Test
    void create() {
        Book book = Book.builder().name(NEW_BOOK).build();
        bookDao.save(book);

        long count = bookDao.count();

        assertThat(count).isEqualTo(1);
    }

    @Test
    void update() {
        Book book = Book.builder().name(NEW_BOOK).build();
        bookDao.save(book);


        String updatedName = "updated name !";
        book.setName(updatedName);

        bookDao.save(book);

        Book result = bookDao.findById(book.getId()).orElse(null);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result).isNotNull();
            softAssertions.assertThat(result.getName()).isEqualTo(updatedName);
        });
    }

    @Test
    void deleteById() {
        Book book = Book.builder().name(NEW_BOOK).build();
        bookDao.save(book);

        bookDao.deleteById(book.getId());

        long count = bookDao.count();

        assertThat(count).isEqualTo(0);
    }

    @Test
    void deleteByIdConstraint() {
        Author author = Author.builder().firstName("test1").lastName("test2").patronymic("test3").build();
        mongoOperations.insert(author);

        Book book = Book.builder()
                .name("test book name")
                .description("test description")
                .authors(List.of(author))
                .build();

        mongoOperations.insert(book);

        author.setBooks(List.of(book));

        mongoOperations.save(author);

        Book result = bookDao.findById(book.getId()).get();

        Assertions.assertThrows(DeleteBookConstraintException.class, () -> bookDao.deleteById(result.getId()));
    }
}