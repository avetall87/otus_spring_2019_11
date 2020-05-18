package ru.spb.avetall.hw14integrationapp.modules.reddit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Children {
    private String kind;
    private RedditChildrenData data;
}
