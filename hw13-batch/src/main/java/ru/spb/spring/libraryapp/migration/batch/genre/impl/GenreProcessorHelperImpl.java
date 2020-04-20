package ru.spb.spring.libraryapp.migration.batch.genre.impl;

import lombok.Builder;
import lombok.Data;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import ru.spb.spring.libraryapp.domain.Genre;
import ru.spb.spring.libraryapp.migration.batch.genre.GenreProcessorHelper;
import ru.spb.spring.libraryapp.migration.jdbc.domain.GenreJdbc;

@Data
@Builder
@Service
public class GenreProcessorHelperImpl implements GenreProcessorHelper {

    public ItemProcessor processor() {
        return Genre -> this.mapToJdbcGenre((Genre) Genre);
    }

    private GenreJdbc mapToJdbcGenre(Genre genre) {
        return GenreJdbc.builder()
                .name(genre.getName())
                .correlationId(genre.getName())
                .build();
    }
}
