package ru.spb.otus.libraryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spb.otus.libraryapp.domain.Genre;
import ru.spb.otus.libraryapp.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    @Override
    @Transactional(readOnly = true)
    public Genre findById(Long id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return null;
    }

    @Override
    @Transactional
    public void update(Genre genre) {

    }

    @Override
    @Transactional
    public void deleteAll() {

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findGenresByBookId(Long bookId) {
        return null;
    }
}
