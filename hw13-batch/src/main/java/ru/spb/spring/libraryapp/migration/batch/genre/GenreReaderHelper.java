package ru.spb.spring.libraryapp.migration.batch.genre;

import org.springframework.batch.item.data.MongoItemReader;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.domain.Genre;

public interface GenreReaderHelper {

    MongoItemReader<Genre> reader();

}
