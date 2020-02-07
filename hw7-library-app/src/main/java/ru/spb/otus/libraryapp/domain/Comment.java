package ru.spb.otus.libraryapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Data
@Table(name = "comments")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;
}
