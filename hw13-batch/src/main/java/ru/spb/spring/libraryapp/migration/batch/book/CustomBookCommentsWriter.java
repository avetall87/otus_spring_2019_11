package ru.spb.spring.libraryapp.migration.batch.book;

import org.springframework.batch.item.ItemWriter;
import ru.spb.spring.libraryapp.migration.jdbc.domain.BookJdbc;

public interface CustomBookCommentsWriter extends ItemWriter<BookJdbc> {
}
