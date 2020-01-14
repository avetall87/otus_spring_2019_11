package ru.spb.otus.libraryapp.service;

import ru.spb.otus.libraryapp.domain.Genre;

import java.util.List;

public interface GenreService {
    Genre findById(Long id);
    List<Genre> findAll();
    void update (Genre genre);
    void deleteAll();
    void deleteById(Long id);

    List<Genre> findGenresByBookId(Long bookId);
}
