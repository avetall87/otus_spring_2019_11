package ru.spb.spring.libraryapp.migration.batch.genre;

import org.springframework.batch.item.ItemProcessor;

public interface GenreProcessorHelper  {
    ItemProcessor processor();
}
