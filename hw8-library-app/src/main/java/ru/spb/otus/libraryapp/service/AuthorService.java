package ru.spb.otus.libraryapp.service;

import ru.spb.otus.libraryapp.domain.Author;

import java.util.List;

public interface AuthorService {
    Author findById(String id);

    List<Author> findAll();

    void update(Author author);

    void deleteById(String id);

    List<Author> findAuthorsByBookId(Long bookId);
}
