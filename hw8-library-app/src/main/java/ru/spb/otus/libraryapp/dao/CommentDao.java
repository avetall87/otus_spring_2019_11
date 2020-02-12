package ru.spb.otus.libraryapp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.spb.otus.libraryapp.domain.Comment;

public interface CommentDao extends MongoRepository<Comment, Long> {

}
