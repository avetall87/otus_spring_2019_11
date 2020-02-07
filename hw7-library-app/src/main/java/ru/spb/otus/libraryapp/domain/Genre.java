package ru.spb.otus.libraryapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name = "genres")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Book.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "books_genres", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Book> books;

}
