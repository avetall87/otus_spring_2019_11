package ru.spb.spring.libraryapp.service.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.spb.spring.libraryapp.AbstractIntegrationTest;
import ru.spb.spring.libraryapp.domain.Author;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.domain.Comment;
import ru.spb.spring.libraryapp.domain.Genre;
import ru.spb.spring.libraryapp.service.BookService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookServiceImplTest extends AbstractIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private MongoOperations mongoOperations;

    @BeforeEach
    void setUp() {
        mongoOperations.dropCollection(Book.class);
    }

    @Test
    void update() {
        Book book = Book.builder().name("name").build();
        mongoOperations.save(book);

        String updatedName = "updated name";
        book.setName(updatedName);

        bookService.update(book);

        Book result = mongoOperations.findById(book.getId(), Book.class);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result).isNotNull();
            softAssertions.assertThat(result.getName()).isEqualTo(updatedName);
        });
    }

    @Test
    void create() {
        Book book = Book.builder().name("name").build();
        bookService.update(book);

        Book result = mongoOperations.findById(book.getId(), Book.class);

        assertThat(result).isNotNull();
    }

    @Test
    void addAuthor() {
        Book book = Book.builder().name("name").build();
        mongoOperations.save(book);

        Author author = Author.builder().firstName("name").build();
        mongoOperations.save(author);

        bookService.addAuthor(book.getId(), author.getId());

        Book result = mongoOperations.findById(book.getId(), Book.class);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result).isNotNull();
            softAssertions.assertThat(result.getAuthors()).isNotEmpty();
        });
    }

    @Test
    void addGenre() {
        Book book = Book.builder().name("name").build();
        mongoOperations.save(book);

        Genre genre = Genre.builder().name("name").build();

        bookService.addGenre(book.getId(), genre);

        Book result = mongoOperations.findById(book.getId(), Book.class);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result).isNotNull();
            softAssertions.assertThat(result.getGenres()).isNotEmpty();
        });
    }

    @Test
    void deleteAuthor() {
        Book book = Book.builder().name("name").build();
        mongoOperations.save(book);

        Author author = Author.builder().firstName("name").build();
        mongoOperations.save(author);

        book.setAuthors(List.of(author));
        mongoOperations.save(book);

        bookService.deleteAuthor(book.getId(), author.getId());

        Book result = mongoOperations.findById(book.getId(), Book.class);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result).isNotNull();
            softAssertions.assertThat(result.getAuthors()).isEmpty();
        });
    }

    @Test
    void deleteGenre() {
        Book book = Book.builder().name("name").build();
        mongoOperations.save(book);

        Genre genre = Genre.builder().name("name").build();

        book.setGenres(List.of(genre));
        mongoOperations.save(book);

        bookService.deleteGenre(book.getId(), genre.getName());

        Book result = mongoOperations.findById(book.getId(), Book.class);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result).isNotNull();
            softAssertions.assertThat(result.getGenres()).isEmpty();
        });
    }

    @Test
    void addComment() {
        Book book = Book.builder().name("name").build();
        mongoOperations.save(book);

        Comment comment = Comment.builder().comment("comment").build();

        bookService.addComment(book.getId(), comment);

        Book result = mongoOperations.findById(book.getId(), Book.class);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result).isNotNull();
            softAssertions.assertThat(result.getComments()).isNotEmpty();
        });
    }
}