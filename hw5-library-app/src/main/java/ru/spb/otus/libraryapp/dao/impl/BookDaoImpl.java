package ru.spb.otus.libraryapp.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.spb.otus.libraryapp.dao.BookDao;
import ru.spb.otus.libraryapp.dao.impl.mapper.BookRowMapper;
import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Genre;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyMap;
import static org.springframework.dao.support.DataAccessUtils.singleResult;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Book findById(Long id) {
        return singleResult(jdbcTemplate.query("select * from books where id = :id", new MapSqlParameterSource("id", id), new BookRowMapper()));
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("select * from books", new BookRowMapper());
    }

    @Override
    public void create(Book book) {
        String sql = "insert into books (name, description) values(:name, :description)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", book.getName());
        source.addValue("description", book.getDescription());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int resultCode = jdbcTemplate.update(sql, source, keyHolder);

        if (resultCode > 0) {
            book.setId((long) keyHolder.getKey());

            linkBookWithAuthors(book.getId(), book.getAuthors());
            linkBookWithGenres(book.getId(), book.getGenres());
        }
    }

    /**
     * Изменяется только книга связи не пересоздаются
     * @param book
     */
    @Override
    public void update(Book book) {
        String sql = "update books set name = :name, description = :description where id = :id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", book.getName());
        source.addValue("description", book.getDescription());
        source.addValue("id", book.getId());

        jdbcTemplate.update(sql, source);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from books", emptyMap());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from books where id = :id", new MapSqlParameterSource("id", id));
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        return jdbcTemplate.query("select * from books b join authors_books ab on b.id = ab.book_id where ab.author_id = :id", new MapSqlParameterSource("id", author.getId()), new BookRowMapper());
    }

    @Override
    public List<Book> findByGenre(Genre genre) {
        return jdbcTemplate.query("select * from books b join books_genres ab on b.id = ab.book_id where ab.genre_id = :id", new MapSqlParameterSource("id", genre.getId()), new BookRowMapper());
    }

    @Override
    public void addAuthor(Long bookId, Author author) {
        linkBookWithAuthors(bookId, Collections.singletonList(author));
    }

    @Override
    public void addGenre(Long bookId, Genre genre) {
        linkBookWithGenres(bookId, Collections.singletonList(genre));
    }

    @Override
    public void deleteAuthor(Long bookId, Author author) {
        unlinkAuthorFromBook(bookId, Collections.singletonList(author));
    }

    @Override
    public void deleteGenre(Long bookId, Genre genre) {
        unlinkGenreFromBook(bookId, Collections.singletonList(genre));
    }

    private void linkBookWithAuthors(Long bookId, List<Author> authors) {
        authors.forEach(author -> {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("author_id", author.getId());
            source.addValue("book_id", bookId);

            jdbcTemplate.update("insert into authors_books (author_id, book_id) values(:author_id, :book_id)", source);
        });
    }

    private void unlinkAuthorFromBook(Long bookId, List<Author> authors) {
        authors.forEach(author -> {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("author_id", author.getId());
            source.addValue("book_id", bookId);

            jdbcTemplate.update("delete from authors_books where author_id = :author_id and book_id = :book_id", source);
        });
    }

    private void linkBookWithGenres(Long bookId, List<Genre> genres) {
        genres.forEach(genre -> {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("book_id", bookId);
            source.addValue("genre_id", genre.getId());

            jdbcTemplate.update("insert into books_genres (book_id, genre_id) values(:book_id, :genre_id)", source);
        });
    }

    private void unlinkGenreFromBook(Long bookId, List<Genre> genres) {
        genres.forEach(genre -> {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("book_id", bookId);
            source.addValue("genre_id", genre.getId());

            jdbcTemplate.update("delete from books_genres where book_id = :book_id and genre_id = :genre_id", source);
        });
    }
}
