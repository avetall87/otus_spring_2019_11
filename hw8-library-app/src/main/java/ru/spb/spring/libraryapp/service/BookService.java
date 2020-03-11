package ru.spb.spring.libraryapp.service;

import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.domain.Comment;
import ru.spb.spring.libraryapp.domain.Genre;

import java.util.List;

public interface BookService {
    Book findById(String id);

    List<Book> findAll();

    void update(Book book);

    void deleteById(String id);

    void addAuthor(String bookId, String authorId);

    void addGenre(String bookId, Genre genre);

    void deleteAuthor(String bookId, String authorId);

    void deleteGenre(String bookId, String genreName);

    void addComment(String bookId, Comment comment);
}
