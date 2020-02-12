package ru.spb.otus.libraryapp.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "authors")
@ToString
@Builder
public class Author {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String patronymic;

    /*@Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Book.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "authors_books", joinColumns = @JoinColumn(name = "author_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))*/
    //private List<Book> books;

}
