package ru.spb.otus.libraryapp.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.spb.otus.libraryapp.dao.AuthorDao;
import ru.spb.otus.libraryapp.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author findById(Long id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public void create(Author author) {
        if (author.getId() == null) {
            em.persist(author);
        } else {
            em.merge(author);
        }
    }

    @Override
    public void update(Author author) {
        em.createQuery("update Author a set " +
                "a.firstName = :first_name, " +
                "a.lastName = :last_name, " +
                "a.patronymic = :patronymic " +
                "where a.id = :id")
                .setParameter("first_name", author.getFirstName())
                .setParameter("last_name", author.getLastName())
                .setParameter("patronymic", author.getPatronymic())
                .setParameter("id", author.getId())
                .executeUpdate();
    }

    @Override
    public void deleteById(Long id) {
        em.detach(Author.builder().id(id).build());

        em.createQuery("delete from Author a where a.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Author> findAuthorsByBookId(Long bookId) {
        return em.createNativeQuery("select a.id, a.first_name, a.last_name, a.patronymic " +
                "from authors a " +
                "join authors_books ab on a.id = ab.author_id " +
                "where ab.book_id = :bookId", Author.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }
}
