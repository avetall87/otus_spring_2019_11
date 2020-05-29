package ru.spb.avetall.redditservice.modules.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.spb.avetall.redditservice.modules.integrationbus.IntegrationGateway;
import ru.spb.avetall.redditservice.modules.reddit.domain.RedditInfo;
import ru.spb.avetall.redditservice.modules.reddit.service.RedditService;
import ru.spb.avetall.redditservice.modules.reddit.service.converter.QuestionConverter;

import java.util.HashMap;
import java.util.Map;

import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedditServiceImpl implements RedditService {

    private final static String REDDIT_URL = "https://www.reddit.com/r/AskReddit/new.json?after=";

    private final IntegrationGateway integrationGateway;
    private final RestTemplate restTemplate;
    private final QuestionConverter questionConverter;

    @Override
    public void askRedditNew() {
        String after = "start";

        while (isNotBlank(after)) {

            ResponseEntity<RedditInfo> response = restTemplate.exchange(
                    REDDIT_URL + after,
                    HttpMethod.GET,
                    createHttpEntityForExport(),
                    RedditInfo.class,
                    1
            );

            // check response
            if (response.getStatusCode() == HttpStatus.OK) {
                RedditInfo redditInfo = response.getBody();

                if (redditInfo != null) {
                    questionConverter.convert(redditInfo).forEach(integrationGateway::send);

                    if (isNotBlank(redditInfo.getData().getAfter()) && !redditInfo.getData().getAfter().equals("null")) {
                        after = redditInfo.getData().getAfter();
                    } else {
                        after = EMPTY;
                    }

                } else {
                    after = EMPTY;
                }

            } else {
                after = EMPTY;
            }
        }
    }

    private HttpEntity<?> createHttpEntityForExport() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("User-agent", "integration-app-0.1");
        headers.setAll(map);

        return new HttpEntity<>(headers);
    }
}
