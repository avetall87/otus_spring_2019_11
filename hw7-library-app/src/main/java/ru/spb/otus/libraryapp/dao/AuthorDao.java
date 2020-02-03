package ru.spb.otus.libraryapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spb.otus.libraryapp.domain.Author;

import java.util.List;

@Repository
public interface AuthorDao extends JpaRepository<Author, Long> {

    @Query(value = "select a.id, a.first_name, a.last_name, a.patronymic " +
            "from authors a join authors_books ab on a.id=ab.author_id " +
            "where ab.book_id = :book_id", nativeQuery = true)
    List<Author> findAuthorsByBookId(@Param("book_id") Long bookId);

    @Modifying
    @Query("update Author a set a.firstName=:firstName, a.lastName=:lastName, a.patronymic=:patronymic where a.id=:id")
    void update(@Param("firstName") String firstName,
                @Param("lastName") String lastName,
                @Param("patronymic") String patronymic,
                @Param("id") Long id);
}
