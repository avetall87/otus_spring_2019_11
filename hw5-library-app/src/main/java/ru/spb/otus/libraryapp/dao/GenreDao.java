package ru.spb.otus.libraryapp.dao;

import ru.spb.otus.libraryapp.domain.Genre;

import java.util.List;

public interface GenreDao {
    Genre findById(Long id);
    List<Genre> findAll();
    void create (Genre genre);
    void update (Genre genre);
    void deleteAll();
    void deleteById(Long id);

    List<Genre> findGenresByBookId(Long bookId);
}
