package ru.spb.otus.libraryapp.service;

import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Comment;

public interface CommentService {
    void add(Book book, Comment comment);
}
