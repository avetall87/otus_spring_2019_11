package ru.spb.avetall.indexerservice.reddit.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.spb.avetall.indexerservice.integrationbus.IntegrationGateway;
import ru.spb.avetall.indexerservice.reddit.service.RedditDataService;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedditScheduler {

    private final RedditDataService redditDataService;
    private final IntegrationGateway integrationGateway;

    @Scheduled(fixedDelayString = "${rest.reddit.scheduler.question:#{60000}}")
    public void pullQuestionLink() {
        try {
            redditDataService.pullQuestionLink().forEach(integrationGateway::sendQuestionLink);
        } catch (Exception ex) {
            log.error("pullQuestionLink exception", ex);
        }
    }
}
