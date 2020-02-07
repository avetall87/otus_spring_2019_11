package ru.spb.otus.libraryapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spb.otus.libraryapp.domain.Comment;

public interface CommentDao extends JpaRepository<Comment, Long> {

}
