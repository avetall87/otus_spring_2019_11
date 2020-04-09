package ru.spb.spring.libraryapp.migration.batch.book.impl;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import ru.spb.spring.libraryapp.migration.batch.book.CustomBookCommentsWriter;
import ru.spb.spring.libraryapp.migration.jdbc.domain.AuthorJdbc;
import ru.spb.spring.libraryapp.migration.jdbc.domain.BookJdbc;
import ru.spb.spring.libraryapp.migration.jdbc.domain.GenreJdbc;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Data
@Slf4j
@Builder
@Service
@RequiredArgsConstructor
public class CustomBookCommentsWriterImpl implements CustomBookCommentsWriter {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void write(List<? extends BookJdbc> books) throws Exception {
        books.forEach(this::create);
    }

    private void create(BookJdbc book) {
        String sql = "insert into books (name, description) " +
                "values(:name, :description)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", book.getName());
        source.addValue("description", book.getDescription());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int resultCode = jdbcTemplate.update(sql, source, keyHolder);

        if (resultCode > 0) {
            book.setId((long) keyHolder.getKey());

            if (isNotEmpty(book.getComments())) {
                book.getComments().forEach(commentJdbc -> createComment(commentJdbc.getComment(), book.getId()));
            }

            if (isNotEmpty(book.getAuthors())) {
                linkBookWithAuthors(book.getId(), book.getAuthors());
            }

            if (isNotEmpty(book.getGenres())) {
                linkBookWithGenres(book.getId(), book.getGenres());
            }
        }
    }

    private void createComment(String comment, Long bookId) {
        String sql = "insert into comments (comment, book_id) " +
                "values(:comment, :book_id)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("comment", isNotBlank(comment) ? comment : "-");
        source.addValue("book_id", bookId);

        jdbcTemplate.update(sql, source);
    }

    private void linkBookWithAuthors(Long bookId, List<AuthorJdbc> authors) {
        authors.forEach(author -> {
            String findAuthorSql = "select id from authors where correlation_id = :correlation_id";

            MapSqlParameterSource authorSource = new MapSqlParameterSource();
            authorSource.addValue("correlation_id", author.getCorrelationId());

            Long authorId;

            List<Long> ids = jdbcTemplate.queryForList(findAuthorSql, authorSource, Long.class);
            authorId = isNotEmpty(ids) ? ids.get(0) : null;

            if (nonNull(authorId)) {
                String insertLinkSql = "insert into authors_books (author_id, book_id) " +
                        "values(:author_id, :book_id)";

                MapSqlParameterSource insertLinkSource = new MapSqlParameterSource();
                insertLinkSource.addValue("author_id", authorId);
                insertLinkSource.addValue("book_id", bookId);

                jdbcTemplate.update(insertLinkSql, insertLinkSource);
            }
        });
    }

    private void linkBookWithGenres(Long bookId, List<GenreJdbc> genres) {
        genres.forEach(genre -> {
            String findAuthorSql = "select id from genres where correlation_id = :correlation_id";

            MapSqlParameterSource authorSource = new MapSqlParameterSource();
            authorSource.addValue("correlation_id", genre.getCorrelationId());

            Long grenreId;

            List<Long> ids = jdbcTemplate.queryForList(findAuthorSql, authorSource, Long.class);
            grenreId = isNotEmpty(ids) ? ids.get(0) : null;

            if (nonNull(grenreId)) {
                String insertLinkSql = "insert into books_genres (book_id, genre_id) " +
                        "values(:book_id, :genre_id)";

                MapSqlParameterSource insertLinkSource = new MapSqlParameterSource();
                insertLinkSource.addValue("book_id", bookId);
                insertLinkSource.addValue("genre_id", grenreId);

                jdbcTemplate.update(insertLinkSql, insertLinkSource);
            }
        });
    }

}
