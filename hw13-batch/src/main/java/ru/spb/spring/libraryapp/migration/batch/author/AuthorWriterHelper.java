package ru.spb.spring.libraryapp.migration.batch.author;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import ru.spb.spring.libraryapp.migration.jdbc.domain.AuthorJdbc;

public interface AuthorWriterHelper {
    JdbcBatchItemWriter<AuthorJdbc> writerJdbc();
}
