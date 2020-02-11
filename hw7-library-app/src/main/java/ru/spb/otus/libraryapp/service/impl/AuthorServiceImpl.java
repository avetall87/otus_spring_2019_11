package ru.spb.otus.libraryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.spb.otus.libraryapp.dao.AuthorDao;
import ru.spb.otus.libraryapp.dao.BookDao;
import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.service.AuthorService;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final BookDao bookDao;

    @Override
    @Transactional(readOnly = true)
    public Author findById(Long id) {
        Assert.notNull(id, "Author id is null !");

        return authorDao.findById(id).orElse(null);
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
            authorDao.update(author.getFirstName(),
                    author.getLastName(),
                    author.getPatronymic(),
                    author.getId());
        } else {
            authorDao.save(author);
        }

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Assert.notNull(id, "Author id is null !");

        authorDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAuthorsByBookId(Long bookId) {
        Assert.notNull(bookId, "Book id is null !");

        Book book = bookDao.findById(bookId).orElse(null);

        if (nonNull(book) && isNotEmpty(book.getAuthors())) {
            return book.getAuthors();
        } else {
            return Collections.emptyList();
        }
    }
}
