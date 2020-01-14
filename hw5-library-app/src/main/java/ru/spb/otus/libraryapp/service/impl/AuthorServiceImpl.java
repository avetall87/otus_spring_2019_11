package ru.spb.otus.libraryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.spb.otus.libraryapp.dao.AuthorDao;
import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.service.AuthorService;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    @Transactional(readOnly = true)
    public Author findById(Long id) {
        Assert.notNull(id, "Author id is null !");

        return authorDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorDao.findAll();
    }

    @Override
    @Transactional
    public void update(Author author) {
        Assert.notNull(author, "Author is null !");

        if (nonNull(author.getId())) {
            authorDao.update(author);
        } else {
            authorDao.create(author);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        authorDao.deleteAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Assert.notNull(id, "Author id is null !");

        authorDao.deleteById(id);
    }

    @Override
    public List<Author> findAuthorsByBookId(Long bookId) {
        return null;
    }
}
