package ru.spb.avetall.indexerservice.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.spb.avetall.common.domain.link.QuestionLink;
import ru.spb.avetall.common.domain.token.SearchToken;
import ru.spb.avetall.indexerservice.reddit.service.RedditDataService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class RedditDataServiceImpl implements RedditDataService {

    private static final String TOKEN_URL = "/api/v1/all/token";
    private static final String QUESTION_URL = "/api/v1/all/question";

    @Value("${rest.reddit.service.url}")
    private String redditServiceUrl;

    private final RestTemplate restTemplate;


    @Override
    public List<QuestionLink> pullQuestionLink() {
        ResponseEntity<QuestionLink[]> entity = restTemplate.getForEntity(redditServiceUrl + QUESTION_URL, QuestionLink[].class);

        if (nonNull(entity.getBody())) {
            return Arrays.asList(entity.getBody());
        }

        return Collections.emptyList();
    }

    @Override
    public List<SearchToken> pullSearchToken() {
        ResponseEntity<SearchToken[]> entity = restTemplate.getForEntity(redditServiceUrl + TOKEN_URL, SearchToken[].class);

        if (nonNull(entity.getBody())) {
            return Arrays.asList(entity.getBody());
        }

        return Collections.emptyList();
    }
}
