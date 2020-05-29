package ru.spb.avetall.indexerservice.service;

import ru.spb.avetall.common.domain.link.QuestionLink;
import ru.spb.avetall.common.domain.token.SearchToken;
import ru.spb.avetall.indexerservice.domain.LinkIndex;

import java.time.LocalDateTime;
import java.util.List;

public interface IndexDataService {

    List<String> getTokensFromQuestions();

    List<QuestionLink> findAllQuestionLink();

    void saveQuestionData(QuestionLink questionLink);
    void deleteAllQuestion();

    void deleteById(String id);

    List<LinkIndex> findAllPage(int page, int size);

    void saveTokenData(SearchToken searchToken);
    void deleteAllToken();

    void deleteNotTodayData(LocalDateTime localDateTime);
}
