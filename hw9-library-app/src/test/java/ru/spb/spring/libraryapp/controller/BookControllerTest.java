package ru.spb.spring.libraryapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.bouncycastle.crypto.tls.ContentType;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.repository.AuthorDao;
import ru.spb.spring.libraryapp.repository.BookDao;
import ru.spb.spring.libraryapp.repository.impl.AuthorDaoCustomImpl;
import ru.spb.spring.libraryapp.repository.impl.BookDaoCustomImpl;
import ru.spb.spring.libraryapp.service.AuthorService;
import ru.spb.spring.libraryapp.service.BookService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookDaoCustomImpl bookDaoCustom;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private AuthorDaoCustomImpl authorDaoCustomImpl;

    @Test
    void getAllBooks() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/book/all")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/book/one/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/book/delete/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void createNewBook() throws Exception {
        Book book = Book.builder()
                .name("name")
                .authors(Collections.emptyList())
                .description("descr")
                .comments(Collections.emptyList())
                .genres(Collections.emptyList())
                .build();

        String jsonData = new ObjectMapper().writeValueAsString(book);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/book/create").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonData))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    void updateBook() throws Exception {
        Book book = Book.builder()
                .id("1")
                .name("name")
                .authors(Collections.emptyList())
                .description("descr")
                .comments(Collections.emptyList())
                .genres(Collections.emptyList())
                .build();

        String jsonData = new ObjectMapper().writeValueAsString(book);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/book/update").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonData))
                .andDo(print()).andExpect(status().isOk());

    }
}