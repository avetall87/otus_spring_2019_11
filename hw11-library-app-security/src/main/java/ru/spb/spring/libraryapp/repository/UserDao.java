package ru.spb.spring.libraryapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.spb.spring.libraryapp.domain.User;

public interface UserDao extends MongoRepository<User, String> {
    User findByLogin(String login);
}
