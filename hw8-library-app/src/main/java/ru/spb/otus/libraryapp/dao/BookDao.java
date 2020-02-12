package ru.spb.otus.libraryapp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.spb.otus.libraryapp.domain.Book;

public interface BookDao extends MongoRepository<Book, Long> {

}
