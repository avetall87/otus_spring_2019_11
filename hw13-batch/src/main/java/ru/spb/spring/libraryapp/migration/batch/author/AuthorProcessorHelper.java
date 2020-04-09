package ru.spb.spring.libraryapp.migration.batch.author;

import org.springframework.batch.item.ItemProcessor;

public interface AuthorProcessorHelper {
    ItemProcessor processor();
}
