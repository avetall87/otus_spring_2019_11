package ru.spb.otus.libraryapp.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.spb.otus.libraryapp.dao.BookDao;
import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Genre;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final EntityManager em;

    @Override
    public Book findById(Long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public void save(Book book) {
        if (isNull(book.getId())) {
            em.persist(book);
        } else {
            em.merge(book);
        }
    }

    /**
     * Изменяется только книга связи не пересоздаются
     *
     * @param book
     */
    @Override
    public void update(Book book) {
        em.createQuery("update Book b set  b.name = :name, b.description = :description where b.id = :id")
                .setParameter("name", book.getName())
                .setParameter("description", book.getDescription())
                .setParameter("id", book.getId())
                .executeUpdate();
    }

    @Override
    public void deleteById(Long id) {
        em.detach(Book.builder().id(id).build());

        em.createQuery("delete from Book b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByAuthor(Author author) {
        return em.createNativeQuery("select b.id, b.name, b.description " +
                "from books b " +
                "join authors_books ab on b.id = ab.book_id " +
                "where ab.author_id = :author_id")
                .setParameter("author_id", author.getId())
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByGenre(Genre genre) {
        return em.createNativeQuery("select b.id, b.name, b.description " +
                "from books b " +
                "join books_genres ab on b.id = ab.book_id " +
                "where ab.genre_id = :genre_id")
                .setParameter("genre_id", genre.getId())
                .getResultList();
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

            /*jdbcTemplate.update("insert into authors_books (author_id, book_id) " +
                    "values(:author_id, :book_id)", source);*/
        });
    }

    private void unlinkAuthorFromBook(Long bookId, List<Author> authors) {
        authors.forEach(author -> {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("author_id", author.getId());
            source.addValue("book_id", bookId);

            /*jdbcTemplate.update("delete from authors_books " +
                    "where author_id = :author_id and book_id = :book_id", source);*/
        });
    }

    private void linkBookWithGenres(Long bookId, List<Genre> genres) {
        genres.forEach(genre -> {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("book_id", bookId);
            source.addValue("genre_id", genre.getId());

            /*jdbcTemplate.update("insert into books_genres (book_id, genre_id) " +
                    "values(:book_id, :genre_id)", source);*/
        });
    }

    private void unlinkGenreFromBook(Long bookId, List<Genre> genres) {
        genres.forEach(genre -> {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("book_id", bookId);
            source.addValue("genre_id", genre.getId());

            /*jdbcTemplate.update("delete from books_genres " +
                    "where book_id = :book_id and genre_id = :genre_id", source);*/
        });
    }
}
