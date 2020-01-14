package ru.spb.otus.libraryapp.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Book {
    private Long id;
    private String name;
    private String description;
    private List<Author> authors;
    private List<Genre> genres;
}
