package ru.spb.avetall.indexerservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.spb.avetall.common.domain.link.QuestionLink;
import ru.spb.avetall.common.domain.token.SearchToken;
import ru.spb.avetall.indexerservice.dao.IndexQuestionDataDao;
import ru.spb.avetall.indexerservice.dao.IndexTokenDataDao;
import ru.spb.avetall.indexerservice.domain.LinkIndex;
import ru.spb.avetall.indexerservice.domain.TokenIndex;
import ru.spb.avetall.indexerservice.service.IndexDataService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexDataServiceImpl implements IndexDataService {

    private final IndexQuestionDataDao indexQuestionDataDao;
    private final IndexTokenDataDao indexTokenDataDao;

    @Override
    public List<String> getTokensFromQuestions() {
        return indexQuestionDataDao.findAll().stream()
                .map(LinkIndex::getToken)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionLink> findAllQuestionLink() {
        return indexQuestionDataDao.findAll().stream().map(this::toQuestionLink).collect(Collectors.toList());
    }

    private QuestionLink toQuestionLink(LinkIndex linkIndex) {
        return QuestionLink.builder()
                .url(linkIndex.getUrl())
                .question(linkIndex.getQuestion())
                .token(linkIndex.getToken())
                .creationDate(toLocalDateTime(linkIndex.getCreationDate()))
                .build();
    }

    private LocalDateTime toLocalDateTime(String creationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(creationDate, formatter);
    }

    @Override
    public void saveQuestionData(QuestionLink questionLink) {
        Assert.notNull(questionLink, "questionLink is null !");

        indexQuestionDataDao.save(toLinkIndex(questionLink));
    }

    @Override
    public void deleteAllQuestion() {
        indexQuestionDataDao.deleteAll();
    }

    @Override
    public void deleteById(String id) {
        indexQuestionDataDao.deleteById(id);
    }

    @Override
    public List<LinkIndex> findAllPage(int page, int size) {
        return indexQuestionDataDao.findAll(PageRequest.of(page, size)).toList();
    }

    @Override
    public void saveTokenData(SearchToken searchToken) {
        Assert.notNull(searchToken, "searchToken is null !");

        indexTokenDataDao.save(toTokenIndex(searchToken));
    }

    @Override
    public void deleteAllToken() {
        indexTokenDataDao.deleteAll();
    }

    @Override
    public void deleteNotTodayData(LocalDateTime localDateTime) {
        String date = localDateTime.getYear() + "-"+ localDateTime.getMonth() + "-" + localDateTime.getDayOfMonth();
        indexQuestionDataDao.deleteByCreationDateNotLike(date);
    }

    private LinkIndex toLinkIndex(QuestionLink questionLink) {
        return LinkIndex
                .builder()
                .question(questionLink.getQuestion())
                .token(questionLink.getToken())
                .url(questionLink.getUrl())
                .creationDate(questionLink.getCreationDate().toString())
                .build();
    }

    private TokenIndex toTokenIndex(SearchToken searchToken) {
        return TokenIndex
                .builder()
                .name(searchToken.getName())
                .build();
    }
}
