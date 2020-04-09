package ru.spb.spring.libraryapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.spb.spring.libraryapp.domain.Author;

@Repository
@RepositoryRestResource(collectionResourceRel = "author", path = "authors")
public interface AuthorDao extends MongoRepository<Author, String>, AuthorDaoCustom {

}
