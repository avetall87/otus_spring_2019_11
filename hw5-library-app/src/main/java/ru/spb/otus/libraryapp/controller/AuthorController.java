package ru.spb.otus.libraryapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.service.AuthorService;

import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@ShellComponent(value = "author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @ShellMethod(value = "Create new author", key = "author_add_new")
    public void add(@ShellOption("--f") String firstName, @ShellOption("--l") String lastName, @ShellOption("--p") String patronymic) {
        Author newAuthor = Author.builder()
                                 .firstName(firstName)
                                 .lastName(lastName)
                                 .patronymic(patronymic)
                                 .build();

        authorService.update(newAuthor);

        System.out.println(format("New author was successfully created with id=%s", newAuthor.getId()));
    }

    @ShellMethod(value = "Find author by id", key = "author_find_by_id")
    public void findById(@ShellOption("--id") Long id) {
        Author author = authorService.findById(id);
        if (nonNull(author)) {
            System.out.println(author);
        } else {
            System.out.println(format("Author was not found with id=%s", id));
        }
    }

    @ShellMethod(value = "Find all authors", key = "author_find_all")
    public void findAll() {
        List<Author> authors = authorService.findAll();

        if (isNotEmpty(authors)) {
            authors.forEach(System.out::println);
        } else {
            System.out.println("Authors not found !");
        }
    }

    @ShellMethod(value = "Update author by id", key = "update_author")
    public void update(@ShellOption("--id") Long id, @ShellOption("--f") String firstName, @ShellOption("--l") String lastName, @ShellOption("--p") String patronymic) {

        authorService.update(Author.builder()
                                   .id(id)
                                   .firstName(firstName)
                                   .lastName(lastName)
                                   .patronymic(patronymic)
                                   .build());

        System.out.println(format("Author was successfully updated with id=%s", id));
    }

    @ShellMethod(value = "Delete author by id", key = "author_delete_by_id")
    public void deleteById(@ShellOption("--id") Long id) {
        authorService.deleteById(id);

        System.out.println(format("Author was successfully deleted by id=%s", id));
    }

    @ShellMethod(value = "Delete all authors", key = "author_delete_all")
    public void deleteAll() {
        authorService.deleteAll();

        System.out.println("All authors was successfully deleted !");
    }

}
