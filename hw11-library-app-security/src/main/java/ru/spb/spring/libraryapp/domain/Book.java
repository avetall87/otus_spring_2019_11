package ru.spb.spring.libraryapp.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(value = "books")
@Builder
@ToString(exclude = "authors")
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    private String id;

    private String name;

    private String description;

    @DBRef(lazy = true)
    @Indexed
    private List<Author> authors;

    private List<Genre> genres;

    private List<Comment> comments;
}
