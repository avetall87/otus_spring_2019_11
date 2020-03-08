package ru.spb.spring.libraryapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import ru.spb.spring.libraryapp.domain.Book;
import ru.spb.spring.libraryapp.events.exception.DeleteBookConstraintException;
import ru.spb.spring.libraryapp.service.BookService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        List<Book> books = bookService.findAll();

        // Что-бы не далать DTO из-за того что на front едет список комментариев (пока структура Front-а и backend-а не сильно раходятся)
        books.forEach(book -> book.setComments(null));
        return books;
    }

    @GetMapping("/one/{id}")
    public Book getOne(@PathVariable String id) {
        return bookService.findById(id);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            bookService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (DeleteBookConstraintException ex) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(ex.getMessage());
        }
    }

    @PostMapping("/create")
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewBook(@RequestBody Book book) {
        Assert.notNull(book, "Книга не передана !");
        Assert.isNull(book.getId(), "Создание невозможно, так как присутствует идентификатор книги !");
        bookService.update(book);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@RequestBody Book book) {
        bookService.update(book);
    }
}
