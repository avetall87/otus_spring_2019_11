package ru.spb.avetall.hw14integrationapp.modules.reddit.domain;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RedditInfo {
    private String kind;
    private RedditData data;
}
