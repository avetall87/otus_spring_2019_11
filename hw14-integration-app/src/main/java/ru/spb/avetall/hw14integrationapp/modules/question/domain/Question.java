package ru.spb.avetall.hw14integrationapp.modules.question.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "questions")
@Entity
@Data
@Builder
@ToString
@NamedEntityGraph(name = "token-entity-graphs", attributeNodes = {@NamedAttributeNode(value = "token")})
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorFullName;
    private String title;
    private String url;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    @ManyToOne(targetEntity = Token.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "token_id")
    private Token token;
}
