package ru.spb.avetall.common.domain.token;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchToken {
    private String name;
}
