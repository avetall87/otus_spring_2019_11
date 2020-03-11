package ru.spb.spring.libraryapp.service.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.spb.spring.libraryapp.AbstractIntegrationTest;
import ru.spb.spring.libraryapp.domain.Author;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.service.AuthorService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorServiceImplTest extends AbstractIntegrationTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private MongoOperations mongoOperations;

    @BeforeEach
    void setUp() {
        mongoOperations.dropCollection(Author.class);
    }

    @Test
    void update() {
        Author author = Author.builder().firstName("first name").build();
        mongoOperations.save(author);

        String updatedFirstName = "updated firstName";
        author.setFirstName(updatedFirstName);

        authorService.update(author);

        Author result = mongoOperations.findById(author.getId(), Author.class);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result).isNotNull();
            softAssertions.assertThat(result.getFirstName()).isEqualTo(updatedFirstName);
        });
    }

    @Test
    void create() {
        Author author = Author.builder().firstName("first name").build();
        authorService.update(author);

        Author result = mongoOperations.findById(author.getId(), Author.class);

        assertThat(result).isNotNull();
    }

    @Test
    void findAuthorsByBookId() {
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

        List<Author> results = authorService.findAuthorsByBookId(book.getId());

        SoftAssertions.assertSoftly(softAssertions -> softAssertions.assertThat(results).isNotEmpty());
    }
}