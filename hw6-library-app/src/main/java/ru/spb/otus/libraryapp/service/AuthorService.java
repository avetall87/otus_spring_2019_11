package ru.spb.otus.libraryapp.service;

import ru.spb.otus.libraryapp.domain.Author;

import java.util.List;

public interface AuthorService {
    Author findById(Long id);

    List<Author> findAll();

    void update(Author author);

    void deleteAll();

    void deleteById(Long id);

    List<Author> findAuthorsByBookId(Long bookId);
}
