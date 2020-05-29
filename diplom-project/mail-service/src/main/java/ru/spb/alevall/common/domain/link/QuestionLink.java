package ru.spb.alevall.common.domain.link;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionLink {
    private String token;
    private String url;
    private String question;
    private LocalDateTime creationDate;
}
