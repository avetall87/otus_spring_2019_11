package ru.spb.avetall.hw14integrationapp.modules.reddit.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.spb.avetall.hw14integrationapp.modules.reddit.service.RedditService;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedditGrabberScheduler {

    private final RedditService redditService;

    @Scheduled(fixedDelay = 10000L)
    public void runGrabber() {
        try {
            redditService.askRedditNew();
        } catch (Exception ex) {
          log.error("RedditGrabberScheduler exception", ex);
        }
    }
}
