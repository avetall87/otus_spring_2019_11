package ru.spb.spring.libraryapp.migration.batch.genre.impl;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.domain.Genre;
import ru.spb.spring.libraryapp.migration.batch.book.BookReaderHelper;
import ru.spb.spring.libraryapp.migration.batch.genre.GenreReaderHelper;

import java.util.HashMap;

@Data
@Builder
@Service
@RequiredArgsConstructor
public class GenreReaderHelperImpl implements GenreReaderHelper {

    private final MongoOperations mongoOperations;

    public MongoItemReader<Genre> reader() {
        return new MongoItemReaderBuilder<Genre>()
                .name("mongoBookItemReader")
                .jsonQuery("{}")
                .targetType(Genre.class)
                .template(mongoOperations)
                .sorts(new HashMap<>())
                .build();
    }

}
