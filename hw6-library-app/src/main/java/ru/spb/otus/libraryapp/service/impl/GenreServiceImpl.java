package ru.spb.otus.libraryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.spb.otus.libraryapp.dao.GenreDao;
import ru.spb.otus.libraryapp.domain.Genre;
import ru.spb.otus.libraryapp.service.GenreService;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    @Transactional(readOnly = true)
    public Genre findById(Long id) {
        Assert.notNull(id, "Genre id is null !");

        return genreDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genreDao.findAll();
    }

    @Override
    @Transactional
    public void update(Genre genre) {
        Assert.notNull(genre, "Genre is null !");

        if (nonNull(genre.getId())) {
            genreDao.update(genre);
        } else {
            genreDao.create(genre);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Assert.notNull(id, "Genre id is null !");

        genreDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findGenresByBookId(Long bookId) {
        Assert.notNull(bookId, "Book id is null !");
        return genreDao.findGenresByBookId(bookId);
    }
}
