package ru.spb.avetall.indexerservice.reddit.service;

import ru.spb.avetall.common.domain.link.QuestionLink;
import ru.spb.avetall.common.domain.token.SearchToken;

import java.util.List;

public interface RedditDataService {
    List<SearchToken> pullSearchToken();
    List<QuestionLink> pullQuestionLink();
}
