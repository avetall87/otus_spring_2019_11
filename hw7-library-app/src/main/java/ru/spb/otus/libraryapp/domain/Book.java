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
@Table(name = "books")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "comment-entity-graphs", attributeNodes = {@NamedAttributeNode(value = "comments")})
@NamedEntityGraph(name = "author-entity-graphs", attributeNodes = {@NamedAttributeNode(value = "authors")})
@NamedEntityGraph(name = "genre-entity-graphs", attributeNodes = {@NamedAttributeNode(value = "genres")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Author.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "authors_books", joinColumns = @JoinColumn(name = "author_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Author> authors;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "books_genres", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.EAGER, targetEntity = Comment.class, cascade = CascadeType.ALL)
    @JoinTable(name = "comments", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Comment> comments;

}
