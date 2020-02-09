package ru.spb.otus.libraryapp.dao;

import ru.spb.otus.libraryapp.domain.Comment;

public interface CommentDao {
    void save(Comment comment);
}
