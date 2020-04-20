package ru.spb.spring.libraryapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.spb.spring.libraryapp.domain.Book;

@RepositoryRestResource(collectionResourceRel = "book", path = "books")
public interface BookDao extends MongoRepository<Book, String>, BookDaoCustom {

}
