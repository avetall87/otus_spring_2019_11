package ru.spb.spring.libraryapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.spb.spring.libraryapp.domain.Author;

@Repository
public interface AuthorDao extends MongoRepository<Author, String>, AuthorDaoCustom {

}
