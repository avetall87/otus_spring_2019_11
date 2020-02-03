package ru.spb.otus.libraryapp.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.spb.otus.libraryapp.dao.CommentDao;
import ru.spb.otus.libraryapp.domain.Comment;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {

    private final EntityManager em;

    @Override
    public void save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }
}
