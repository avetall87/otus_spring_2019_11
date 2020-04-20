package ru.spb.spring.libraryapp.migration.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentJdbc {
    private Long id;

    private String comment;

    private Long bookId;
}
