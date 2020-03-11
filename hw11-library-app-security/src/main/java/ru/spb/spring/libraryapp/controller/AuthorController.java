package ru.spb.spring.libraryapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spb.spring.libraryapp.domain.Author;
import ru.spb.spring.libraryapp.service.AuthorService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/all")
    public List<Author> getAllBooks() {
        return authorService.findAll();
    }

    @GetMapping("/book/{bookId}")
    public List<Author> getOne(@PathVariable String bookId) {
        return authorService.findAuthorsByBookId(bookId);
    }
}
