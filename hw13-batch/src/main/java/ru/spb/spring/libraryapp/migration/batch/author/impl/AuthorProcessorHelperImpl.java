package ru.spb.spring.libraryapp.migration.batch.author.impl;

import lombok.Builder;
import lombok.Data;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import ru.spb.spring.libraryapp.domain.Author;
import ru.spb.spring.libraryapp.migration.batch.author.AuthorProcessorHelper;
import ru.spb.spring.libraryapp.migration.jdbc.domain.AuthorJdbc;

@Data
@Builder
@Service
public class AuthorProcessorHelperImpl implements AuthorProcessorHelper {

    public ItemProcessor processor() {
        return Author-> this.mapToJdbcAuthor((Author) Author);
    }

    public AuthorJdbc mapToJdbcAuthor(Author author) {
        return AuthorJdbc.builder()
                .firstName(author.getFirstName())
                .lastName(author.getFirstName())
                .patronymic(author.getPatronymic())
                .correlationId(author.getId())
                .build();
    }

}
