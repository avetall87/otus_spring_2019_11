package ru.spb.spring.libraryapp.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.spb.spring.libraryapp.AbstractIntegrationTest;
import ru.spb.spring.libraryapp.domain.Author;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.events.exception.DeleteAuthorConstraintException;
import ru.spb.spring.libraryapp.repository.AuthorDao;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorDaoTest extends AbstractIntegrationTest {

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private MongoOperations mongoOperations;

    @BeforeEach
    void setUp() {
        mongoOperations.dropCollection(Author.class);
    }

    @Test
    void findById() {

        Author author = Author.builder().firstName("test1").lastName("test2").patronymic("test3").build();

        mongoOperations.insert(author);

        Author result = authorDao.findById(author.getId()).orElse(null);

        assertThat(result).isNotNull();
    }

    @Test
    void findAll() {
        authorDao.save(Author.builder().firstName("test1").lastName("test2").patronymic("test3").build());

        List<Author> authors = mongoOperations.findAll(Author.class);

        assertThat(authors).isNotEmpty();
    }

    @Test
    void create() {
        Author author = Author.builder().firstName("test1").lastName("test2").patronymic("test3").build();

        authorDao.save(author);

        Author result = mongoOperations.findById(author.getId(), Author.class);

        assertThat(result).isNotNull();
    }

    @Test
    void update() {
        Author author = Author.builder().firstName("test1").lastName("test2").patronymic("test3").build();
        mongoOperations.insert(author);

        author.setFirstName("super test");

        authorDao.update(author);

        Author updatedAuthor = authorDao.findById(author.getId()).orElse(null);

        assertThat(updatedAuthor.getFirstName()).isEqualTo("super test");
    }

    @Test
    void deleteById() {
        Author author = Author.builder().firstName("test1").lastName("test2").patronymic("test3").build();

        mongoOperations.insert(author);

        authorDao.deleteById(author.getId());

        Author result = authorDao.findById(author.getId()).orElse(null);

        assertThat(result).isNull();
    }

    @Test
    void deleteByIdConstraint() {
        Author author = Author.builder().firstName("test1").lastName("test2").patronymic("test3").build();
        mongoOperations.insert(author);

        Book book = Book.builder()
                .name("test book name")
                .description("test description")
                .authors(List.of(author))
                .build();

        mongoOperations.insert(book);

        author.setBooks(List.of(book));

        mongoOperations.save(author);

        Author result = authorDao.findById(author.getId()).get();

        Assertions.assertThrows(DeleteAuthorConstraintException.class, () -> authorDao.deleteById(result.getId()));
    }
}