package ru.spb.otus.libraryapp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.spb.otus.libraryapp.domain.Genre;

@Repository
public interface GenreDao extends MongoRepository<Genre, Long> {

}
