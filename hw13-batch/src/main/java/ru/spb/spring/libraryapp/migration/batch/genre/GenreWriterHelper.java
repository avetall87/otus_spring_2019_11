package ru.spb.spring.libraryapp.migration.batch.genre;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import ru.spb.spring.libraryapp.migration.jdbc.domain.BookJdbc;
import ru.spb.spring.libraryapp.migration.jdbc.domain.GenreJdbc;

public interface GenreWriterHelper {
    JdbcBatchItemWriter<GenreJdbc> writerJdbc();
}
