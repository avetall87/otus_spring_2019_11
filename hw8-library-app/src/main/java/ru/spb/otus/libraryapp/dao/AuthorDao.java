package ru.spb.otus.libraryapp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.spb.otus.libraryapp.domain.Author;

@Repository
public interface AuthorDao extends MongoRepository<Author, String> {

}
