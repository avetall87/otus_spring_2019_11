package ru.spb.otus.libraryapp.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Author {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
}
