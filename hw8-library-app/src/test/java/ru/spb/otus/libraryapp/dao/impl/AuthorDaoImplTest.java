package ru.spb.otus.libraryapp.dao.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.spb.otus.libraryapp.dao.AuthorDao;
import ru.spb.otus.libraryapp.domain.Author;

import java.util.List;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class AuthorDaoImplTest {

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

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void findAll() {
        authorDao.save(Author.builder().firstName("test1").lastName("test2").patronymic("test3").build());

        List<Author> authors = mongoOperations.findAll(Author.class);

        Assertions.assertThat(authors).isNotEmpty();
    }

    @Test
    void create() {
        Author author = Author.builder().firstName("test1").lastName("test2").patronymic("test3").build();

        mongoOperations.insert(author);

        Author result = authorDao.findById(author.getId()).orElse(null);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void update() {
        Author author = Author.builder().firstName("test1").lastName("test2").patronymic("test3").build();
        mongoOperations.insert(author);

        author.setFirstName("super test");

        authorDao.save(author);

        Author updatedAuthor = authorDao.findById(author.getId()).orElse(null);

        Assertions.assertThat(updatedAuthor.getFirstName()).isEqualTo("super test");
    }

    @Test
    void deleteById() {
        Author author = Author.builder().firstName("test1").lastName("test2").patronymic("test3").build();

        mongoOperations.insert(author);

        authorDao.deleteById(author.getId());

        Author result = authorDao.findById(author.getId()).orElse(null);

        Assertions.assertThat(result).isNull();
    }
}