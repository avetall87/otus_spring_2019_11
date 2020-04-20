package ru.spb.spring.libraryapp.migration.batch.book;

import org.springframework.batch.item.data.MongoItemReader;
import ru.spb.spring.libraryapp.domain.Book;

public interface BookReaderHelper {

    MongoItemReader<Book> reader();

}
