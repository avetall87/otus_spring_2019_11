package ru.spb.spring.libraryapp.migration.batch.author;

import org.springframework.batch.item.data.MongoItemReader;
import ru.spb.spring.libraryapp.domain.Author;

public interface AuthorReaderHelper {

    MongoItemReader<Author> reader();

}
