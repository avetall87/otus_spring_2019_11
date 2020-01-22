package ru.spb.otus.libraryapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Genre;
import ru.spb.otus.libraryapp.service.BookService;

import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@ShellComponent
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @ShellMethod(value = "Create new book", key = "book_add_new")
    public void add(String name, String description) {
        Book book = Book.builder().name(name).description(description).build();
        bookService.update(book);

        System.out.println(format("New book was successfully created with id=%s", book.getId()));
    }

    @ShellMethod(value = "Find book by id", key = "book_find_by_id")
    public void findById(Long id) {
        Book book = bookService.findById(id);

        if (nonNull(book)) {
            System.out.println(book);
        } else {
            System.out.println(format("Book was not found with id=%s", id));
        }
    }

    @ShellMethod(value = "Find all books", key = "book_find_all")
    public void findAll() {
        List<Book> books = bookService.findAll();

        if (isNotEmpty(books)) {
            books.forEach(System.out::println);
        } else {
            System.out.println("Books not found !");
        }
    }

    @ShellMethod(value = "Update book by id", key = "update_book")
    public void update(Long id, String name, String description) {
        Book book = Book.builder().name(name).description(description).build();

        bookService.update(book);

        System.out.println(format("Book was successfully updated with id=%s", id));
    }

    @ShellMethod(value = "Delete book by id", key = "book_delete_by_id")
    public void deleteById(Long id) {
        bookService.deleteById(id);

        System.out.println(format("Book was successfully deleted by id=%s", id));
    }

    @ShellMethod(value = "Delete all genres", key = "book_delete_all")
    public void deleteAll() {
        bookService.deleteAll();

        System.out.println("All books was successfully deleted !");
    }

    @ShellMethod(value = "Find all books by author id", key = "book_find_by_author_id")
    public void findAllByAuthorId(Long authorId) {
        List<Book> books = bookService.findByAuthor(Author.builder().id(authorId).build());

        if (isNotEmpty(books)) {
            books.forEach(System.out::println);
        } else {
            System.out.println("Books not found !");
        }
    }

    @ShellMethod(value = "Find all books by genre id", key = "book_find_by_genre_id")
    public void findAllByGenreId(Long genreId) {
        List<Book> books = bookService.findByGenre(Genre.builder().id(genreId).build());

        if (isNotEmpty(books)) {
            books.forEach(System.out::println);
        } else {
            System.out.println("Books not found !");
        }
    }

    @ShellMethod(value = "Add author to book by id", key = "book_add_author_to_book_by_id")
    public void addAuthorToBook(Long bookId, Long authorId) {
        bookService.addAuthor(Book.builder().id(bookId).build(), Author.builder().id(authorId).build());

        System.out.println(format("Author(id=%s) was successfully add to book with id=%s", authorId, bookId));
    }

    @ShellMethod(value = "Add genre to book by id", key = "book_add_genre_to_book_by_id")
    public void addGenreToBook(Long bookId, Long genreId) {
        bookService.addGenre(Book.builder().id(bookId).build(), Genre.builder().id(genreId).build());

        System.out.println(format("Genre(id=%s) was successfully add to book with id=%s", genreId, bookId));
    }

    @ShellMethod(value = "Delete author from book by id", key = "book_delete_author_from_book_by_id")
    public void deleteAuthor(Long bookId, Long authorId) {
        bookService.deleteAuthor(Book.builder().id(bookId).build(), Author.builder().id(authorId).build());

        System.out.println(format("Author(id=%s) was successfully removed from book with id=%s", authorId, bookId));
    }

    @ShellMethod(value = "Delete genre from book by id", key = "book_delete_genre_from_book_by_id")
    public void genreAuthor(Long bookId, Long authorId) {
        bookService.deleteAuthor(Book.builder().id(bookId).build(), Author.builder().id(authorId).build());

        System.out.println(format("Author(id=%s) was successfully removed from book with id=%s", authorId, bookId));
    }

}