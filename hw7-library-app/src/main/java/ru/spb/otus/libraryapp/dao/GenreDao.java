package ru.spb.otus.libraryapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spb.otus.libraryapp.domain.Genre;

import java.util.List;

@Repository
public interface GenreDao extends JpaRepository<Genre, Long> {

    @Modifying
    @Query("update Genre g set g.name = :name where g.id =:id")
    void update(@Param("name") String name, @Param("id") Long id);

    @Query(value = "select g.id, g.name " +
            "from genres g " +
            "join books_genres bg on g.id=bg.genre_id " +
            "where bg.book_id = :book_id", nativeQuery = true)
    List<Genre> findGenresByBookId(@Param("book_id") Long bookId);
}
