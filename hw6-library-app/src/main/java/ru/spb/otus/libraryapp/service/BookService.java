package ru.spb.otus.libraryapp.service;

import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Genre;

import java.util.List;

public interface BookService {
    Book findById(Long id);

    List<Book> findAll();

    void update(Book book);

    void deleteById(Long id);

    List<Book> findByAuthor(Author author);

    List<Book> findByGenre(Genre genre);

    void addAuthor(Book book, Author author);

    void addGenre(Book book, Genre genre);

    void deleteAuthor(Book book, Author author);

    void deleteGenre(Book book, Genre genre);

    void addComment(Book book, String comment);
}
