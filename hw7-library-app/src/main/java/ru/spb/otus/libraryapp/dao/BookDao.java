package ru.spb.otus.libraryapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.spb.otus.libraryapp.domain.Book;

public interface BookDao extends JpaRepository<Book, Long> {

    @Modifying
    @Query("update Book b set b.name=:name, b.description=:description where b.id=:id")
    void update(@Param("name") String name, @Param("description") String description, @Param("id") Long id);
}
