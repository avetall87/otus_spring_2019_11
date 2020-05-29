package ru.spb.avetall.redditservice.modules.reddit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedditData {
    private String modHash;
    private Double dist;
    private List<Children> children;
    private String after;
    private String before;

}
