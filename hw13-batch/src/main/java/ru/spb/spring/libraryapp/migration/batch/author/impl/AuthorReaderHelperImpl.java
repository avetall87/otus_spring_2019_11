package ru.spb.spring.libraryapp.migration.batch.author.impl;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import ru.spb.spring.libraryapp.domain.Author;
import ru.spb.spring.libraryapp.migration.batch.author.AuthorReaderHelper;

import java.util.HashMap;

@Data
@Builder
@Service
@RequiredArgsConstructor
public class AuthorReaderHelperImpl implements AuthorReaderHelper {

    private final MongoOperations mongoOperations;

    public MongoItemReader<Author> reader() {
        return new MongoItemReaderBuilder<Author>()
                .name("mongoAuthorItemReader")
                .jsonQuery("{}")
                .targetType(Author.class)
                .template(mongoOperations)
                .sorts(new HashMap<>())
                .build();
    }

}
