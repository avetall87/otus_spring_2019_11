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
@ShellComponent
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @ShellMethod(value = "Create new author", key = "add_new_author")
    private void add(String firstName, String lastName, String patronymic) {
        Author newAuthor = Author.builder()
                                 .firstName(firstName)
                                 .lastName(lastName)
                                 .patronymic(patronymic)
                                 .build();

        authorService.update(newAuthor);

        System.out.println(format("New author was successfully created with id=%s", newAuthor.getId()));
    }

    @ShellMethod(value = "Find author by id", key = "find_author_by_id")
    private void findById(Long id) {
        Author author = authorService.findById(id);
        if (nonNull(author)) {
            System.out.println(author);
        } else {
            System.out.println(format("Author was not found with id=%s", id));
        }
    }

    @ShellMethod(value = "Find all authors", key = "find_all_authors")
    private void findAll() {
        authorService.findAll().forEach(System.out::println);
    }

}
