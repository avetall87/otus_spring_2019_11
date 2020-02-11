package ru.spb.otus.libraryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.spb.otus.libraryapp.dao.BookDao;
import ru.spb.otus.libraryapp.dao.GenreDao;
import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Genre;
import ru.spb.otus.libraryapp.service.GenreService;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    private final BookDao bookDao;

    @Override
    @Transactional(readOnly = true)
    public Genre findById(Long id) {
        Assert.notNull(id, "Genre id is null !");

        return genreDao.findById(id).orElse(null);
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
            genreDao.update(genre.getName(), genre.getId());
        } else {
            genreDao.save(genre);
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

        Book book = bookDao.findById(bookId).orElse(null);

        if (nonNull(book) && isNotEmpty(book.getGenres())) {
            return book.getGenres();
        } else {
            return Collections.emptyList();
        }
    }
}
