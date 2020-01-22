package ru.spb.otus.libraryapp.dao.impl.mapper;

import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;
import ru.spb.otus.libraryapp.domain.Genre;

import java.sql.ResultSet;

public class GenreRowMapper implements RowMapper<Genre> {

    @Override
    @SneakyThrows
    public Genre mapRow(ResultSet rs, int rowNum) {
        return Genre.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
