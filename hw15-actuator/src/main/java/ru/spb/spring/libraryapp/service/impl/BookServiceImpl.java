package ru.spb.spring.libraryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.spb.spring.libraryapp.domain.Author;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.domain.Comment;
import ru.spb.spring.libraryapp.domain.Genre;
import ru.spb.spring.libraryapp.repository.AuthorDao;
import ru.spb.spring.libraryapp.repository.BookDao;
import ru.spb.spring.libraryapp.service.BookService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;

    @Override
    @Transactional(readOnly = true)
    public Book findById(String id) {
        Assert.notNull(id, "Book id is null !");
        return getBook(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    @Transactional
    public void update(Book book) {
        bookDao.save(book);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Assert.notNull(id, "Book id is null !");

        bookDao.deleteById(id);
    }

    @Override
    @Transactional
    public void addAuthor(String bookId, String authorId) {
        Book book = getBook(bookId);

        Author author = authorDao.findById(authorId).orElseThrow(() -> new IllegalArgumentException("Author id is null !"));

        if (isNotEmpty(book.getAuthors())) {
            book.getAuthors().add(author);
        } else {
            book.setAuthors(List.of(author));
        }

        bookDao.save(book);
    }

    @Override
    @Transactional
    public void addGenre(String bookId, Genre genre) {
        Book book = getBook(bookId);

        if (isNotEmpty(book.getGenres())) {
            book.getGenres().add(genre);
        } else {
            book.setGenres(List.of(genre));
        }

        bookDao.save(book);
    }

    @Override
    @Transactional
    public void deleteAuthor(String bookId, String authorId) {
        Assert.notNull(authorId, "Author id is null !");

        Book book = getBook(bookId);

        if (isNotEmpty(book.getAuthors())) {
            List<Author> processedAuthors = book.getAuthors().stream()
                    .filter(author -> !Objects.equals(author.getId(), authorId))
                    .collect(Collectors.toList());
            book.setAuthors(processedAuthors);

            bookDao.save(book);
        }
    }

    @Override
    @Transactional
    public void deleteGenre(String bookId, String genreName) {
        Assert.notNull(genreName, "Genre id is null !");

        Book book = getBook(bookId);
        List<Genre> genres = book.getGenres().stream()
                .filter(genre -> genreName.equalsIgnoreCase(genre.getName()))
                .collect(Collectors.toList());
        book.getGenres().removeAll(genres);

        bookDao.save(book);
    }

    @Override
    @Transactional
    public void addComment(String bookId, Comment comment) {
        Assert.notNull(comment, "Comment is null !");

        Book book = getBook(bookId);

        if (isNotEmpty(book.getComments())) {
            book.getComments().add(comment);
        } else {
            book.setComments(List.of(comment));
        }

        bookDao.save(book);
    }

    private Book getBook(String bookId) {
        return bookDao.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book id is null !"));
    }
}
