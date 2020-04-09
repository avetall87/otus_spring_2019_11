package ru.spb.spring.libraryapp.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.spb.spring.libraryapp.domain.Author;
import ru.spb.spring.libraryapp.events.exception.DeleteAuthorConstraintException;
import ru.spb.spring.libraryapp.repository.AuthorDao;

import static java.lang.String.format;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Component
@RequiredArgsConstructor
public class AuthorDeleteConstraintListener extends AbstractMongoEventListener<Author> {

    private static final String AUTHORS_COLLECTION_NAME = "authors";

    private final AuthorDao authorDao;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        super.onBeforeDelete(event);

        String collectionName = event.getCollectionName();

        if (AUTHORS_COLLECTION_NAME.equalsIgnoreCase(collectionName)) {
            String authorId = String.valueOf(event.getSource().get("_id"));

            Author author = authorDao.findById(authorId).orElseThrow(() -> new IllegalStateException(format("Author not found with id=%s", authorId)));

            if (isNotEmpty(author.getBooks())) {
                throw new DeleteAuthorConstraintException("Нельзя удалить автора, так как есть связь с книгой !");
            }

        }

    }
}
