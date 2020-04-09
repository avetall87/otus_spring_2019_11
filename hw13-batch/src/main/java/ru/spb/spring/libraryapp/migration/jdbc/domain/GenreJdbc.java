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
public class GenreJdbc {

    private Long id;

    private String name;

    private String correlationId;

    private List<BookJdbc> books;

}
