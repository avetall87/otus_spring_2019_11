package ru.spb.otus.libraryapp.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.spb.otus.libraryapp.dao.BookDao;
import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Comment;
import ru.spb.otus.libraryapp.domain.Genre;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

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
        if (book.getId() == null) {
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
        if (nonNull(author)) {
            Book book = em.find(Book.class, bookId);

            if (nonNull(book)) {
                if (isNotEmpty(book.getAuthors())) {
                    book.getAuthors().add(author);
                } else {
                    List<Author> authors = new ArrayList<>();
                    authors.add(author);
                    book.setAuthors(authors);
                }
                save(book);
            }
        }
    }

    @Override
    public void addGenre(Long bookId, Genre genre) {
        if (nonNull(genre)) {
            Book book = em.find(Book.class, bookId);

            if (nonNull(book)) {
                if (isNotEmpty(book.getAuthors())) {
                    book.getGenres().add(genre);
                } else {
                    List<Genre> genres = new ArrayList<>();
                    genres.add(genre);
                    book.setGenres(genres);
                }
                save(book);
            }
        }
    }

    @Override
    public void deleteAuthor(Long bookId, Author author) {
        em.detach(em.find(Author.class, author.getId()));
        em.createQuery("delete from Author a where a.id = :id").setParameter("id", author.getId()).executeUpdate();
    }

    @Override
    public void deleteGenre(Long bookId, Genre genre) {
        em.detach(em.find(Genre.class, genre.getId()));
        em.createQuery("delete from Genre g where g.id = :id").setParameter("id", genre.getId()).executeUpdate();
    }

    @Override
    public void addComment(Long bookId, String comment) {
        Book book = em.find(Book.class, bookId);

        if (nonNull(book)) {
            book.getComments().add(Comment.builder().comment(comment).build());

            save(book);
        }
    }

}
