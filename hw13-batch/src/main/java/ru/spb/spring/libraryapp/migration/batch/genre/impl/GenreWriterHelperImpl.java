package ru.spb.spring.libraryapp.migration.batch.genre.impl;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.stereotype.Service;
import ru.spb.spring.libraryapp.migration.batch.genre.GenreWriterHelper;
import ru.spb.spring.libraryapp.migration.jdbc.domain.GenreJdbc;

import javax.sql.DataSource;


@Data
@Builder
@Service
@RequiredArgsConstructor
public class GenreWriterHelperImpl implements GenreWriterHelper {

    private final DataSource dataSource;

    public JdbcBatchItemWriter<GenreJdbc> writerJdbc() {
        return new JdbcBatchItemWriterBuilder<GenreJdbc>()
                .dataSource(dataSource)
                .sql("insert into genres (name, correlation_id) values (?, ?)")
                .itemPreparedStatementSetter((item, ps) -> {
                    ps.setString(1, item.getName());
                    ps.setString(2, item.getName());

                })
                .build();
    }

}
