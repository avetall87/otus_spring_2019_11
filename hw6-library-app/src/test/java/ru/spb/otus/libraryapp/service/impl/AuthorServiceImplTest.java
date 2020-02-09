package ru.spb.otus.libraryapp.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.spb.otus.libraryapp.dao.AuthorDao;
import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.service.AuthorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Проверка сервиса по работе с авторами")
class AuthorServiceImplTest {

    private AuthorService authorService;
    private AuthorDao authorDao;

    @BeforeEach
    void setUp() {
        authorDao = Mockito.mock(AuthorDao.class);
        authorService = new AuthorServiceImpl(authorDao);
    }

    @Test
    @DisplayName("Посик автора по идентификатору")
    void findById() {
        when(authorDao.findById(Mockito.anyLong())).thenReturn(Author.builder().id(1L).build());
        Author author = authorService.findById(1L);
        assertEquals(1, (long) author.getId());
    }

    @Test
    @DisplayName("Посик автора по идентификатору, проверка исключения IllegalArgumentException если не указан идентификатор автора")
    void findByIdGetException() {
        assertThrows(IllegalArgumentException.class, () -> authorService.findById(null));
    }

    @Test
    @DisplayName("Создание нового автора")
    void createNewAuthor() {
        Author author = Author.builder().firstName("test").lastName("test").patronymic("tesr").build();

        authorService.update(author);
        assertNotNull(author);
    }

    @Test
    @DisplayName("Создание нового автора, проверка исключения IllegalArgumentException если не указан автор")
    void createNewAuthorGetException() {
        assertThrows(IllegalArgumentException.class, () -> authorService.update(null));
    }

    @Test
    @DisplayName("Удаление автора по идентификатору, проверка исключения IllegalArgumentException если не указан идентификатор автора")
    void deleteByIdGetException() {
        assertThrows(IllegalArgumentException.class, () -> authorService.findById(null));
    }

}