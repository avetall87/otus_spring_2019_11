package ru.spb.avetall.hw14integrationapp.modules.reddit.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedditChildrenData {

    @JsonProperty("author_fullname")
    private String authorFullName;

    private String title;
    private String url;

    @JsonProperty("created_utc")
    private Long created;
}
