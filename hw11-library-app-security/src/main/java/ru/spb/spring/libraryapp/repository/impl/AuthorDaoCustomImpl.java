package ru.spb.spring.libraryapp.repository.impl;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import ru.spb.spring.libraryapp.domain.Author;
import ru.spb.spring.libraryapp.repository.AuthorDaoCustom;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class AuthorDaoCustomImpl implements AuthorDaoCustom {

    private final String[] EXCLUDE_UPDATE_FIELDS = {"books"};

    private final MongoOperations mongoOperations;

    @Override
    public void update(Author author) {
        Document doc = new Document();
        mongoOperations.getConverter().write(author, doc);
        Update update = Update.fromDocument(doc, EXCLUDE_UPDATE_FIELDS);

        mongoOperations.updateFirst(query(where("_id").is(author.getId())), update, Author.class);
    }
}
