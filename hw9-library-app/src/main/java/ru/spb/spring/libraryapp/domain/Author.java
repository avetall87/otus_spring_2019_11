package ru.spb.spring.libraryapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(value = "authors")
@ToString(exclude = "books")
@Builder
public class Author {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String patronymic;

    @DBRef(lazy = true)
    @JsonIgnore
    @Indexed
    private List<Book> books;

}
