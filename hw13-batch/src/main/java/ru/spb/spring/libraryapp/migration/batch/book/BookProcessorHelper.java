package ru.spb.spring.libraryapp.migration.batch.book;

import org.springframework.batch.item.ItemProcessor;

public interface BookProcessorHelper {
    ItemProcessor processor();
}
