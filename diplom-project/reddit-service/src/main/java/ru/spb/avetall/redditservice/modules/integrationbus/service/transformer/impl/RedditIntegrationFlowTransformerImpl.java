package ru.spb.avetall.redditservice.modules.integrationbus.service.transformer.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.spb.avetall.redditservice.modules.reddit.domain.Children;
import ru.spb.avetall.redditservice.modules.reddit.domain.RedditChildrenData;
import ru.spb.avetall.redditservice.modules.reddit.domain.RedditData;
import ru.spb.avetall.redditservice.modules.integrationbus.service.transformer.RedditIntegrationFlowTransformer;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@Service
public class RedditIntegrationFlowTransformerImpl implements RedditIntegrationFlowTransformer {

    @Override
    public List<RedditChildrenData> transformToRedditChildrenDataList(RedditData redditData) {
        if (isNull(redditData)) {
            return emptyList();
        }

        return isNotEmpty(redditData.getChildren()) ? redditData.getChildren().stream().map(Children::getData).collect(Collectors.toList()) : emptyList();
    }
}
