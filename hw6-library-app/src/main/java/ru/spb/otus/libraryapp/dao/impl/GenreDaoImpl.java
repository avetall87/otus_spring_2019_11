package ru.spb.otus.libraryapp.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.spb.otus.libraryapp.dao.GenreDao;
import ru.spb.otus.libraryapp.domain.Genre;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final EntityManager em;

    @Override
    public Genre findById(Long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre", Genre.class).getResultList();
    }

    @Override
    public void create(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
        } else {
            em.merge(genre);
        }
    }

    @Override
    public void update(Genre genre) {
        em.createQuery("update Genre g set g.name = :name where g.id = :id")
                .setParameter("name", genre.getName())
                .setParameter("id", genre.getId())
                .executeUpdate();
    }

    @Override
    public void deleteById(Long id) {
        em.detach(Genre.builder().id(id).build());

        em.createQuery("delete from Genre g where g.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Genre> findGenresByBookId(Long bookId) {
        return em.createNativeQuery("select g.id, g.name " +
                "from genres g " +
                "join books_genres bg on g.id=bg.genre_id " +
                "where bg.book_id = :bookId", Genre.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }
}
