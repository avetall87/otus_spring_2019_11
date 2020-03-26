package ru.spb.spring.libraryapp.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.events.exception.DeleteBookConstraintException;
import ru.spb.spring.libraryapp.repository.BookDao;

import static java.lang.String.format;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Component
@RequiredArgsConstructor
public class BookDeleteConstraintListener extends AbstractMongoEventListener<Book> {

    private static final String BOOKS_COLLECTION_NAME = "books";

    private final BookDao bookDao;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        super.onBeforeDelete(event);

        String collectionName = event.getCollectionName();

        if (BOOKS_COLLECTION_NAME.equalsIgnoreCase(collectionName)) {
            String bookId = String.valueOf(event.getSource().get("_id"));

            Book book = bookDao.findById(bookId).orElseThrow(() -> new IllegalStateException(format("Book not found with id=%s", bookId)));

            if (isNotEmpty(book.getAuthors())) {
                throw new DeleteBookConstraintException("Нельзя удалить книгу, так как есть связь с автором !");
            }

        }

    }
}
