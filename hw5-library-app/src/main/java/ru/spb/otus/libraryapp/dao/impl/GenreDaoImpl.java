package ru.spb.otus.libraryapp.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.spb.otus.libraryapp.dao.GenreDao;
import ru.spb.otus.libraryapp.dao.impl.mapper.GenreRowMapper;
import ru.spb.otus.libraryapp.domain.Genre;

import java.util.Collections;
import java.util.List;

import static org.springframework.dao.support.DataAccessUtils.singleResult;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Genre findById(Long id) {
        return singleResult(jdbcTemplate.query("select * from genres where id = :id", new MapSqlParameterSource("id",id), new GenreRowMapper()));
    }

    @Override
    public List<Genre> findAll() {
        return jdbcTemplate.query("select * from genres",new GenreRowMapper());
    }

    @Override
    public void create(Genre genre) {
        String sql = "insert into genres (name) values(:name)";

        jdbcTemplate.update(sql, new MapSqlParameterSource("name", genre.getName()));
    }

    @Override
    public void update(Genre genre) {
        String sql = "update genres set name = :name where id = :id";

        jdbcTemplate.update(sql, new MapSqlParameterSource("id", genre.getId()));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from genres", Collections.emptyMap());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from genres where id = :id", new MapSqlParameterSource("id", id));
    }

    @Override
    public List<Genre> findGenresByBookId(Long bookId) {
        return jdbcTemplate.query("select * from genres g join books_genres bg on g.id=bg.genre_id where bg.book_id = :bookId", new MapSqlParameterSource("bookId", bookId), new GenreRowMapper());
    }
}
