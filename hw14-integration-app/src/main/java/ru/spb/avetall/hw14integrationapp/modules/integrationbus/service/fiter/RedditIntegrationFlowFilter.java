package ru.spb.avetall.hw14integrationapp.modules.integrationbus.service.fiter;

import ru.spb.avetall.hw14integrationapp.modules.question.domain.Question;

public interface RedditIntegrationFlowFilter {
    boolean checkRedditPost(Question question);
}
