package ru.spb.avetall.hw14integrationapp.modules.integrationbus.service.transformer;

import ru.spb.avetall.hw14integrationapp.modules.reddit.domain.RedditChildrenData;
import ru.spb.avetall.hw14integrationapp.modules.reddit.domain.RedditData;

import java.util.List;

public interface RedditIntegrationFlowTransformer {
    List<RedditChildrenData> transformToRedditChildrenDataList(RedditData redditData);
}
