package ru.spb.spring.libraryapp.service;

import ru.spb.spring.libraryapp.domain.Author;

import java.util.List;

public interface AuthorService {
    Author findById(String id);

    List<Author> findAll();

    void update(Author author);

    void deleteById(String id);

    List<Author> findAuthorsByBookId(String bookId);
}
