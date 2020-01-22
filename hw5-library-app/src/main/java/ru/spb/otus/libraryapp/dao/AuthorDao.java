package ru.spb.otus.libraryapp.dao;

import ru.spb.otus.libraryapp.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author findById(Long id);
    List<Author> findAll();
    void create (Author author);
    void update (Author author);
    void deleteAll();
    void deleteById(Long id);

    List<Author> findAuthorsByBookId(Long bookId);
}
