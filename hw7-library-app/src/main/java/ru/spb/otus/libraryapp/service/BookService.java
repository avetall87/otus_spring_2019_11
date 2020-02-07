package ru.spb.otus.libraryapp.service;

import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Comment;

import java.util.List;

public interface BookService {
    Book findById(Long id);

    List<Book> findAll();

    void update(Book book);

    void deleteById(Long id);

    List<Book> findByAuthor(Long id);

    List<Book> findByGenre(Long id);

    void addAuthor(Long bookId, Long authorId);

    void addGenre(Long bookId, Long genreId);

    void deleteAuthor(Long bookId, Long authorId);

    void deleteGenre(Long bookId, Long genreId);

    void addComment(Long bookId, Comment comment);
}
