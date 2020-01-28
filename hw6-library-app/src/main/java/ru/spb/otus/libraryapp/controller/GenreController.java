package ru.spb.otus.libraryapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.spb.otus.libraryapp.domain.Genre;
import ru.spb.otus.libraryapp.service.GenreService;

import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@ShellComponent
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @ShellMethod(value = "Create new genre", key = "genre_add_new")
    public void add(@ShellOption("--n") String name) {
        Genre genre = Genre.builder().name(name).build();

        genreService.update(genre);

        System.out.println(format("New genre was successfully created with id=%s", genre.getId()));
    }

    @ShellMethod(value = "Find genre by id", key = "genre_find_by_id")
    public void findById(@ShellOption("--id") Long id) {
        Genre genre = genreService.findById(id);
        if (nonNull(genre)) {
            System.out.println(genre);
        } else {
            System.out.println(format("Genre was not found with id=%s", id));
        }
    }

    @ShellMethod(value = "Find all genres", key = "genre_find_all")
    public void findAll() {
        List<Genre> genres = genreService.findAll();

        if (isNotEmpty(genres)) {
            genres.forEach(System.out::println);
        } else {
            System.out.println("Genres not found !");
        }
    }

    @ShellMethod(value = "Update genre by id", key = "update_genre")
    public void update(@ShellOption("--id") Long id, @ShellOption("--n") String name) {
        Genre genre = Genre.builder().id(id).name(name).build();

        genreService.update(genre);

        System.out.println(format("Genre was successfully updated with id=%s", id));
    }

    @ShellMethod(value = "Delete author by id", key = "genre_delete_by_id")
    public void deleteById(@ShellOption("--id") Long id) {
        genreService.deleteById(id);

        System.out.println(format("Genre was successfully deleted by id=%s", id));
    }

    @ShellMethod(value = "Find all genres by book id", key = "genre_find_all_by_bok_id")
    public void findAllByBookId(Long bookId) {
        genreService.findGenresByBookId(bookId).forEach(System.out::println);
    }

}
