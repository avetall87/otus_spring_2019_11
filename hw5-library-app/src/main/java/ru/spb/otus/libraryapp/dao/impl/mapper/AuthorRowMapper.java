package ru.spb.otus.libraryapp.dao.impl.mapper;

import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;
import ru.spb.otus.libraryapp.domain.Author;

import java.sql.ResultSet;

public class AuthorRowMapper implements RowMapper<Author> {

    @Override
    @SneakyThrows
    public Author mapRow(ResultSet rs, int rowNum) {
        return Author.builder()
                     .id(rs.getLong("id"))
                     .firstName(rs.getString("first_name"))
                     .lastName(rs.getString("last_name"))
                     .patronymic(rs.getString("patronymic"))
                     .build();
    }
}
