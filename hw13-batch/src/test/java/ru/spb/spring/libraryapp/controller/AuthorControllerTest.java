package ru.spb.spring.libraryapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.spb.spring.libraryapp.repository.AuthorDao;
import ru.spb.spring.libraryapp.repository.BookDao;
import ru.spb.spring.libraryapp.repository.impl.AuthorDaoCustomImpl;
import ru.spb.spring.libraryapp.repository.impl.BookDaoCustomImpl;
import ru.spb.spring.libraryapp.service.AuthorService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllBooks() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/author/all")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/author/book/1")).andDo(print()).andExpect(status().isOk());
    }
}