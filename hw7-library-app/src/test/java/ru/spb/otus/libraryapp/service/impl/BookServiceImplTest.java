package ru.spb.otus.libraryapp.service.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ru.spb.otus.libraryapp.controller.AuthorController;
import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Comment;
import ru.spb.otus.libraryapp.domain.Genre;
import ru.spb.otus.libraryapp.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
@Sql(scripts = "classpath:books_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthorController.class))
class BookServiceImplTest {

    private final long BASE_BOOK_ID = 100L;

    @Autowired
    private BookService bookService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findByAuthor() {
        long FIND_AUTHOR_ID = 110L;
        List<Book> books = bookService.findByAuthor(FIND_AUTHOR_ID);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(books).isNotNull();
            softAssertions.assertThat(books.size()).isGreaterThan(0);
        });
    }

    @Test
    void findByGenre() {
        long FIND_GENRE_ID = 110L;

        List<Book> books = bookService.findByGenre(FIND_GENRE_ID);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(books).isNotNull();
            softAssertions.assertThat(books.size()).isGreaterThan(0);
        });

    }

    @Test
    void addAuthor() {
        long ADD_AUTHOR_1_ID = 130L;
        long ADD_AUTHOR_2_ID = 140L;

        bookService.addAuthor(BASE_BOOK_ID, ADD_AUTHOR_1_ID);
        bookService.addAuthor(BASE_BOOK_ID, ADD_AUTHOR_2_ID);

        Book book = bookService.findById(BASE_BOOK_ID);

        List<Author> result = book.getAuthors()
                .stream()
                .filter(author -> (author.getId() == ADD_AUTHOR_1_ID || author.getId() == ADD_AUTHOR_2_ID))
                .collect(Collectors.toList());

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result).isNotNull();
            softAssertions.assertThat(result.size()).isEqualTo(2);
        });
    }

    @Test
    void addGenre() {
        long ADD_GENRE_1_ID = 101L;
        long ADD_GENRE_2_ID = 110L;

        bookService.addGenre(BASE_BOOK_ID, ADD_GENRE_1_ID);
        bookService.addGenre(BASE_BOOK_ID, ADD_GENRE_2_ID);

        Book book = bookService.findById(BASE_BOOK_ID);

        List<Genre> result = book.getGenres()
                .stream()
                .filter(genre -> (genre.getId() == ADD_GENRE_1_ID || genre.getId() == ADD_GENRE_2_ID))
                .collect(Collectors.toList());

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result).isNotNull();
            softAssertions.assertThat(result.size()).isEqualTo(2);
        });
    }

    @Test
    void deleteAuthor() {
        long bookId = 100L;
        long authorId = 110L;

        bookService.deleteAuthor(bookId, authorId);
        Book book = bookService.findById(bookId);

        long count = book.getAuthors().stream().filter(author -> author.getId() == bookId).count();

        SoftAssertions.assertSoftly(softAssertions -> softAssertions.assertThat(count).isEqualTo(0));
    }

    @Test
    void deleteGenre() {
        long bookId = 100L;
        long genreId = 100L;

        bookService.deleteGenre(bookId, genreId);

        Book book = bookService.findById(bookId);

        long count = book.getGenres().stream().filter(genre -> genre.getId() == genreId).count();

        SoftAssertions.assertSoftly(softAssertions -> softAssertions.assertThat(count).isEqualTo(0));
    }

    @Test
    void addComment() {
        long bookId = 100L;
        String newComment = "new comment";

        bookService.addComment(bookId, Comment.builder().comment(newComment).build());

        Book book = bookService.findById(bookId);

        long count = book.getComments().stream().filter(comment -> newComment.equals(comment.getComment())).count();

        SoftAssertions.assertSoftly(softAssertions -> softAssertions.assertThat(count).isEqualTo(1));
    }
}