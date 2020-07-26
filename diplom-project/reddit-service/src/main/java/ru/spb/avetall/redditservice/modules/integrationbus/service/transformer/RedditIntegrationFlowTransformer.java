package ru.spb.avetall.redditservice.modules.integrationbus.service.transformer;

import ru.spb.avetall.redditservice.modules.reddit.domain.RedditChildrenData;
import ru.spb.avetall.redditservice.modules.reddit.domain.RedditData;

import java.util.List;

public interface RedditIntegrationFlowTransformer {
    List<RedditChildrenData> transformToRedditChildrenDataList(RedditData redditData);
}
