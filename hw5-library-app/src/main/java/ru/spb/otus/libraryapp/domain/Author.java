package ru.spb.otus.libraryapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private List<Book> books;
}
