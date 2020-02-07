package ru.spb.otus.libraryapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spb.otus.libraryapp.domain.Genre;

@Repository
public interface GenreDao extends JpaRepository<Genre, Long> {

    @Modifying
    @Query("update Genre g set g.name = :name where g.id =:id")
    void update(@Param("name") String name, @Param("id") Long id);
}
