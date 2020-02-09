package ru.spb.otus.libraryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spb.otus.libraryapp.dao.CommentDao;
import ru.spb.otus.libraryapp.domain.Book;
import ru.spb.otus.libraryapp.domain.Comment;
import ru.spb.otus.libraryapp.service.CommentService;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    @Override
    @Transactional
    public void add(Book book, Comment comment) {
        if (nonNull(book) && nonNull(book.getId())) {
            comment.setBookId(book.getId());
            commentDao.save(comment);
        }
    }
}
