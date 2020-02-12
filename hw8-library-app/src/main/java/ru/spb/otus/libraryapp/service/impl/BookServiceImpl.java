package ru.spb.otus.libraryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.spb.otus.libraryapp.dao.AuthorDao;
import ru.spb.otus.libraryapp.dao.BookDao;
import ru.spb.otus.libraryapp.dao.CommentDao;
import ru.spb.otus.libraryapp.dao.GenreDao;
import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Comment;
import ru.spb.otus.libraryapp.domain.Genre;
import ru.spb.otus.libraryapp.service.BookService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final CommentDao commentDao;

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        Assert.notNull(id, "Book id is null !");
        Book book = getBook(id);
        checkReferenceEntities(book);

        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        List<Book> books = bookDao.findAll();
        books.forEach(this::checkReferenceEntities);

        return books;
    }

    private void checkReferenceEntities(Book book) {

        if (!nonNull(book.getGenres())) {
            book.setGenres(Collections.emptyList());
        }

        if (!nonNull(book.getAuthors())) {
            book.setGenres(Collections.emptyList());
        }

        if (!nonNull(book.getComments())) {
            book.setGenres(Collections.emptyList());
        }
    }

    @Override
    @Transactional
    public void update(Book book) {

        /*if (nonNull(book.getId())) {
            bookDao.update(book.getName(), book.getDescription(), book.getId());
        } else {
            bookDao.save(book);
        }*/
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Assert.notNull(id, "Book id is null !");

        bookDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByAuthor(Long id) {
//        Author author = authorDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Author id is null !"));

        /*if (!isNotEmpty(author.getBooks())) {
            author.setBooks(Collections.emptyList());
        }*/

        return null;//author.getBooks();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByGenre(Long id) {
        Genre genre = genreDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Genre id is null !"));

        if (!isNotEmpty(genre.getBooks())) {
            genre.setBooks(Collections.emptyList());
        }

        return genre.getBooks();
    }

    @Override
    @Transactional
    public void addAuthor(Long bookId, Long authorId) {
        Book book = getBook(bookId);

        // Author author = authorDao.findById(authorId).orElseThrow(() -> new IllegalArgumentException("Author id is null !"));

        /*if (isNotEmpty(book.getAuthors())) {
            book.getAuthors().add(author);
        } else {
            List<Author> authors = new ArrayList<>();
            authors.add(author);
            book.setAuthors(authors);
        }*/

        bookDao.save(book);
    }

    @Override
    @Transactional
    public void addGenre(Long bookId, Long genreId) {
        Book book = getBook(bookId);

        Genre genre = genreDao.findById(genreId).orElseThrow(() -> new IllegalArgumentException("Genre id is null !"));

        if (isNotEmpty(book.getGenres())) {
            book.getGenres().add(genre);
        } else {
            List<Genre> genres = new ArrayList<>();
            genres.add(genre);
            book.setGenres(genres);
        }

        bookDao.save(book);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long bookId, Long authorId) {
        Assert.notNull(authorId, "Author id is null !");

        Book book = getBook(bookId);

        if (isNotEmpty(book.getAuthors())) {
            List<Author> processedAuthors = book.getAuthors().stream().filter(author -> !Objects.equals(author.getId(), authorId)).collect(Collectors.toList());
            book.setAuthors(processedAuthors);

            bookDao.save(book);
        }
    }

    @Override
    @Transactional
    public void deleteGenre(Long bookId, Long genreId) {
        Assert.notNull(genreId, "Genre id is null !");

        Book book = getBook(bookId);

        if (isNotEmpty(book.getGenres())) {
            List<Genre> processedGenres = book.getGenres().stream().filter(genre -> !Objects.equals(genre.getId(), genreId)).collect(Collectors.toList());
            book.setGenres(processedGenres);

            bookDao.save(book);
        }
    }

    @Override
    @Transactional
    public void addComment(Long bookId, Comment comment) {
        Assert.notNull(comment, "Comment is null !");

        comment.setBook(getBook(bookId));
        commentDao.save(comment);
    }

    private Book getBook(Long bookId) {
        return bookDao.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book id is null !"));
    }
}
