package ru.spb.otus.libraryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.spb.otus.libraryapp.dao.BookDao;
import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Genre;
import ru.spb.otus.libraryapp.service.AuthorService;
import ru.spb.otus.libraryapp.service.BookService;
import ru.spb.otus.libraryapp.service.GenreService;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        Assert.notNull(id, "Book id is null !");

        Book book = bookDao.findById(id);
        book.setAuthors(authorService.findAuthorsByBookId(book.getId()));
        book.setGenres(genreService.findGenresByBookId(book.getId()));
        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        List<Book> books = bookDao.findAll();

        books.forEach(book -> {
            book.setAuthors(authorService.findAuthorsByBookId(book.getId()));
            book.setGenres(genreService.findGenresByBookId(book.getId()));
        });

        return books;
    }

    @Override
    @Transactional
    public void update(Book book) {
        if (nonNull(book.getId())) {
            bookDao.update(book);
        } else {
            bookDao.create(book);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        bookDao.deleteAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Assert.notNull(id, "Book id is null !");

        bookDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByAuthor(Author author) {
        return bookDao.findByAuthor(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByGenre(Genre genre) {
        return bookDao.findByGenre(genre);
    }

    @Override
    @Transactional
    public void addAuthor(Book book, Author author) {
        bookDao.addAuthor(book.getId(), author);
        book.getAuthors().add(author);
    }

    @Override
    @Transactional
    public void addGenre(Book book, Genre genre) {
        bookDao.addGenre(book.getId(), genre);
        book.getGenres().add(genre);
    }

    @Override
    @Transactional
    public void deleteAuthor(Book book, Author author) {
        bookDao.deleteAuthor(book.getId(), author);
        book.getAuthors().remove(author);
    }

    @Override
    @Transactional
    public void deleteGenre(Book book, Genre genre) {
        bookDao.deleteGenre(book.getId(), genre);
        book.getGenres().remove(genre);
    }
}
