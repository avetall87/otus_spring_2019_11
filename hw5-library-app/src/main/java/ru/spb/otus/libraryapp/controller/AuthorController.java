package ru.spb.otus.libraryapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.spb.otus.libraryapp.domain.Author;
import ru.spb.otus.libraryapp.service.AuthorService;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Slf4j
@ShellComponent(value = "author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @ShellMethod(value = "Create new author", key = "author_add_new")
    public void add(String firstName, String lastName, String patronymic) {
        Author newAuthor = Author.builder()
                                 .firstName(firstName)
                                 .lastName(lastName)
                                 .patronymic(patronymic)
                                 .build();

        authorService.update(newAuthor);

        System.out.println(format("New author was successfully created with id=%s", newAuthor.getId()));
    }

    @ShellMethod(value = "Find author by id", key = "author_find_by_id")
    public void findById(Long id) {
        Author author = authorService.findById(id);
        if (nonNull(author)) {
            System.out.println(author);
        } else {
            System.out.println(format("Author was not found with id=%s", id));
        }
    }

    @ShellMethod(value = "Find all authors", key = "author_find_all")
    public void findAll() {
        authorService.findAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Update author by id", key = "update_author")
    public void update(Long id, String firstName, String lastName, String patronymic) {

        authorService.update(Author.builder()
                                   .id(id)
                                   .firstName(firstName)
                                   .lastName(lastName)
                                   .patronymic(patronymic)
                                   .build());

        System.out.println(format("Author was successfully updated with id=%s", id));
    }

    @ShellMethod(value = "Delete author by id", key = "author_delete_by_id")
    public void deleteById(Long id) {
        authorService.deleteById(id);

        System.out.println(format("Author was successfully deleted by id=%s", id));
    }

    @ShellMethod(value = "Delete all authors", key = "author_delete_all")
    public void deleteAll() {
        authorService.deleteAll();

        System.out.println("All authors was successfully deleted !");
    }

}
