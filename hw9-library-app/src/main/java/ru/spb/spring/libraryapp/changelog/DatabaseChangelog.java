package ru.spb.spring.libraryapp.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.spb.spring.libraryapp.domain.Author;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.domain.Comment;
import ru.spb.spring.libraryapp.domain.Genre;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "addAuthor", author = "avetall87")
    public void someChange1(MongoTemplate mongoTemplate) {
        mongoTemplate.save(Author.builder().firstName("first name1").lastName("last name").patronymic("patronymic").build());
    }

    @ChangeSet(order = "002", id = "addBookWithAuthor", author = "avetall87")
    public void someChange2(MongoTemplate mongoTemplate) {

        Author author = Author.builder().firstName("first name2").lastName("last name").patronymic("patronymic").build();
        mongoTemplate.save(author);

        Book book = Book.builder().name("testName1").description("some description").build();
        mongoTemplate.save(book);

        book.setAuthors(List.of(author));
        mongoTemplate.save(book);

        author.setBooks(List.of(book));
        mongoTemplate.save(author);
    }

    @ChangeSet(order = "003", id = "bookWithGenresAndComments", author = "avetall87")
    public void someChange3(MongoTemplate mongoTemplate) {

        Author author = Author.builder().firstName("first name3").lastName("last name").patronymic("patronymic").build();
        mongoTemplate.save(author);

        Book book = Book.builder().name("testName2").description("some description").build();
        mongoTemplate.save(book);

        book.setAuthors(List.of(author));
        mongoTemplate.save(book);

        author.setBooks(List.of(book));
        mongoTemplate.save(author);

        book.setComments(List.of(Comment.builder().comment("comment").build()));

        book.setGenres(List.of(Genre.builder().name("genre").build()));

        mongoTemplate.save(book);
    }

    @ChangeSet(order = "004", id = "manyAuthors", author = "avetall87")
    public void someChange4(MongoTemplate mongoTemplate) {

        Author author = Author.builder().firstName("first name3").lastName("last name").patronymic("patronymic").build();
        mongoTemplate.save(author);

        Author author2 = Author.builder().firstName("first name4").lastName("last name4").patronymic("patronymic4").build();
        mongoTemplate.save(author2);

        Book book = Book.builder().name("testName2").description("some description").build();
        mongoTemplate.save(book);

        book.setAuthors(List.of(author, author2));
        mongoTemplate.save(book);

        author.setBooks(List.of(book));
        mongoTemplate.save(author);

        author2.setBooks(List.of(book));
        mongoTemplate.save(author);

        book.setComments(List.of(Comment.builder().comment("comment").build()));

        book.setGenres(List.of(Genre.builder().name("genre").build()));

        mongoTemplate.save(book);
    }

}
