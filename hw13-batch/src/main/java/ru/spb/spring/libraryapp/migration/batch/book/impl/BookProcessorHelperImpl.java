package ru.spb.spring.libraryapp.migration.batch.book.impl;

import lombok.Builder;
import lombok.Data;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import ru.spb.spring.libraryapp.domain.Author;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.domain.Comment;
import ru.spb.spring.libraryapp.domain.Genre;
import ru.spb.spring.libraryapp.migration.batch.book.BookProcessorHelper;
import ru.spb.spring.libraryapp.migration.jdbc.domain.AuthorJdbc;
import ru.spb.spring.libraryapp.migration.jdbc.domain.BookJdbc;
import ru.spb.spring.libraryapp.migration.jdbc.domain.CommentJdbc;
import ru.spb.spring.libraryapp.migration.jdbc.domain.GenreJdbc;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Data
@Builder
@Service
public class BookProcessorHelperImpl implements BookProcessorHelper {

    public ItemProcessor processor() {
        return Book -> this.mapToJdbcAuthor((Book) Book);
    }

    private BookJdbc mapToJdbcAuthor(Book book) {
        return BookJdbc.builder()
                .comments(mapComments(book.getComments()))
                .authors(mapAuthors(book.getAuthors()))
                .genres(mapGenres(book.getGenres()))
                .description(book.getDescription())
                .name(book.getName())
                .build();
    }

    private List<CommentJdbc> mapComments(List<Comment> comments) {
        if (isNull(comments)) {
            return null;
        }

        return comments.stream()
                .filter(Objects::nonNull)
                .map(comment -> CommentJdbc.builder().comment(comment.getComment()).build())
                .collect(Collectors.toList());
    }

    private List<AuthorJdbc> mapAuthors(List<Author> authors) {
        if (isNull(authors)) {
            return null;
        }

        return authors.stream()
                .filter(Objects::nonNull)
                .map(author -> AuthorJdbc.builder()
                        .firstName(author.getFirstName())
                        .lastName(author.getLastName())
                        .patronymic(author.getPatronymic())
                        .correlationId(author.getId())
                        .build())
                .collect(Collectors.toList());
    }

    private List<GenreJdbc> mapGenres(List<Genre> genres) {
        if (isNull(genres)) {
            return null;
        }

        return genres.stream()
                .filter(Objects::nonNull)
                .map(genre -> GenreJdbc.builder()
                        .name(genre.getName())
                        .correlationId(genre.getName())
                        .build())
                .collect(Collectors.toList());
    }

}
