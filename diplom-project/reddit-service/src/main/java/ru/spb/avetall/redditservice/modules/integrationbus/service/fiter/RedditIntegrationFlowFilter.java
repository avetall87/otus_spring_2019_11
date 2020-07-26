package ru.spb.avetall.redditservice.modules.integrationbus.service.fiter;

import ru.spb.avetall.redditservice.modules.question.domain.Question;

public interface RedditIntegrationFlowFilter {
    boolean checkRedditPost(Question question);
}
