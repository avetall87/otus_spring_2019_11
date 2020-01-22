package ru.spb.otus.libraryapp.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.spb.otus.libraryapp.dao.AuthorDao;
import ru.spb.otus.libraryapp.dao.impl.mapper.AuthorRowMapper;
import ru.spb.otus.libraryapp.domain.Author;

import java.util.List;

import static java.util.Collections.emptyMap;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Author findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("select id, first_name, last_name, patronymic " +
                            "from authors " +
                            "where id = :id",
                    new MapSqlParameterSource("id", id),
                    new AuthorRowMapper());

        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (Exception ex) {
            log.error("Author findById exception", ex);
            return null;
        }
    }

    @Override
    public List<Author> findAll() {
        return jdbcTemplate.query("select id, first_name, last_name, patronymic from authors", new AuthorRowMapper());
    }

    @Override
    public void create(Author author) {
        String sql = "insert into authors (first_name, last_name, patronymic) " +
                "values(:first_name, :last_name, :patronymic)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("first_name", author.getFirstName());
        source.addValue("last_name", author.getLastName());
        source.addValue("patronymic", author.getPatronymic());

        jdbcTemplate.update(sql, source, keyHolder);
        author.setId((long) keyHolder.getKey());
    }

    @Override
    public void update(Author author) {
        String sql = "update authors set" +
                "first_name = :first_name, " +
                "last_name = :last_name, " +
                "patronymic = :patronymic " +
                "where id = :id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("first_name", author.getFirstName());
        source.addValue("last_name", author.getLastName());
        source.addValue("patronymic", author.getPatronymic());
        source.addValue("id", author.getId());

        jdbcTemplate.update(sql, source);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from authors", emptyMap());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from authors where id = :id";

        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    @Override
    public List<Author> findAuthorsByBookId(Long bookId) {
        return jdbcTemplate.query("select id, first_name, last_name, patronymic " +
                "from authors a " +
                "join authors_books ab on a.id = ab.author_id " +
                "where ab.book_id = :book_id", new MapSqlParameterSource("book_id", bookId), new AuthorRowMapper());
    }
}
