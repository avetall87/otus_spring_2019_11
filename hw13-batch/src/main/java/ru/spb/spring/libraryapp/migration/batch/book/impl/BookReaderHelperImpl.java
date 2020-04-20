package ru.spb.spring.libraryapp.migration.batch.book.impl;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.migration.batch.author.AuthorProcessorHelper;
import ru.spb.spring.libraryapp.migration.batch.book.BookReaderHelper;

import java.util.HashMap;

@Data
@Builder
@Service
@RequiredArgsConstructor
public class BookReaderHelperImpl implements BookReaderHelper {

    private final MongoOperations mongoOperations;

    public MongoItemReader<Book> reader() {
        return new MongoItemReaderBuilder<Book>()
                .name("mongoBookItemReader")
                .jsonQuery("{}")
                .targetType(Book.class)
                .template(mongoOperations)
                .sorts(new HashMap<>())
                .build();
    }

}
