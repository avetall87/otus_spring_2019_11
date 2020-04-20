package ru.spb.spring.libraryapp.migration.batch.author.impl;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.stereotype.Service;
import ru.spb.spring.libraryapp.migration.batch.author.AuthorWriterHelper;
import ru.spb.spring.libraryapp.migration.jdbc.domain.AuthorJdbc;

import javax.sql.DataSource;

@Data
@Builder
@Service
@RequiredArgsConstructor
public class AuthorWriterHelperImpl implements AuthorWriterHelper {

    private final DataSource dataSource;

    public JdbcBatchItemWriter<AuthorJdbc> writerJdbc() {
        return new JdbcBatchItemWriterBuilder<AuthorJdbc>()
                .dataSource(dataSource)
                .sql("insert into authors(first_name, last_name, patronymic, correlation_id) values(?, ?, ?, ?)")
                .itemPreparedStatementSetter((item, ps) -> {
                    ps.setString(1, item.getFirstName());
                    ps.setString(2, item.getLastName());
                    ps.setString(3, item.getPatronymic());
                    ps.setString(4, item.getCorrelationId());
                })
                .build();
    }

}
