package ru.spb.otus.libraryapp.dao.impl.mapper;

import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;
import ru.spb.otus.libraryapp.domain.Book;

import java.sql.ResultSet;

public class BookRowMapper implements RowMapper<Book> {

    @Override
    @SneakyThrows
    public Book mapRow(ResultSet rs, int rowNum) {
        return Book.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .build();
    }
}
