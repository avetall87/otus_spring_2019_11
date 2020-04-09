package ru.spb.spring.libraryapp.migration.jdbc.domain;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString(exclude = {"authors", "genres"})
@NoArgsConstructor
@AllArgsConstructor
public class BookJdbc implements Cloneable {
    private Long id;

    private String name;

    private String description;

    private List<AuthorJdbc> authors;

    private List<GenreJdbc> genres;

    private List<CommentJdbc> comments;

    @Override
    public BookJdbc clone() throws CloneNotSupportedException {
        return (BookJdbc) super.clone();
    }
}
