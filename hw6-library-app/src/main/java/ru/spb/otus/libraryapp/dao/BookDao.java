package ru.spb.otus.libraryapp.dao;

import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Genre;

import java.util.List;

public interface BookDao {
    Book findById(Long id);

    List<Book> findAll();

    void create(Book book);

    void update(Book book);

    void deleteAll();

    void deleteById(Long id);

    List<Book> findByAuthor(Author author);

    List<Book> findByGenre(Genre genre);

    void addAuthor(Long bookId, Author author);

    void addGenre(Long bookId, Genre genre);

    void deleteAuthor(Long bookId, Author author);

    void deleteGenre(Long bookId, Genre genre);
}
