package ru.spb.spring.libraryapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.spb.spring.libraryapp.domain.Book;

public interface BookDao extends MongoRepository<Book, String>, BookDaoCustom {

}
