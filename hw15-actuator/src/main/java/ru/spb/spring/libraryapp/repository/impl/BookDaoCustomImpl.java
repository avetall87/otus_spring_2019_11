package ru.spb.spring.libraryapp.repository.impl;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.repository.BookDaoCustom;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class BookDaoCustomImpl implements BookDaoCustom {

    private final String[] EXCLUDE_UPDATE_FIELDS = {"authors", "genres", "comments"};

    private final MongoOperations mongoOperations;

    @Override
    public void update(Book book) {
        Document doc = new Document();
        mongoOperations.getConverter().write(book, doc);
        Update update = Update.fromDocument(doc, EXCLUDE_UPDATE_FIELDS);

        mongoOperations.updateFirst(query(where("_id").is(book.getId())), update, Book.class);
    }
}
