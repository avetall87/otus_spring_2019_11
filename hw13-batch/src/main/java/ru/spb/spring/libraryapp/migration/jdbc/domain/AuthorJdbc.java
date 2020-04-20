package ru.spb.spring.libraryapp.migration.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorJdbc {

    private Long id;

    private String firstName;

    private String lastName;

    private String patronymic;

    private List<BookJdbc> books;

    private String correlationId;
}
