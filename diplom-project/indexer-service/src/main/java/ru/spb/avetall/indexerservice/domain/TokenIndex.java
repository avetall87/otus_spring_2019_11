package ru.spb.avetall.indexerservice.domain;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "tokendocument", type = "tokens")
public class TokenIndex {
    @Id
    private String id;

    private String name;
}
