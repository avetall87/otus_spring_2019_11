package ru.spb.avetall.redditservice.modules.reddit.service.converter.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.spb.avetall.redditservice.modules.question.domain.Question;
import ru.spb.avetall.redditservice.modules.reddit.domain.RedditChildrenData;
import ru.spb.avetall.redditservice.modules.reddit.domain.RedditInfo;
import ru.spb.avetall.redditservice.modules.reddit.service.converter.QuestionConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Service
public class QuestionConverterImpl implements QuestionConverter {

    private static final String ZONE_NAME = "Europe/Moscow";

    @Override
    public List<Question> convert(RedditInfo redditInfo) {

        if (isNull(redditInfo) || isNull(redditInfo.getData()) || isEmpty(redditInfo.getData().getChildren())) {
            return Collections.emptyList();
        }

        return redditInfo
                .getData()
                .getChildren()
                .stream()
                .map(children -> convertFromReddirChildrenData(children.getData()))
                .collect(Collectors.toList());
    }

    private Question convertFromReddirChildrenData(RedditChildrenData redditChildrenData) {
        return Question.builder()
                .authorFullName(redditChildrenData.getAuthorFullName())
                .title(redditChildrenData.getTitle())
                .url(redditChildrenData.getUrl())
                .creationDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(redditChildrenData.getCreated()), ZoneId.of(ZONE_NAME)))
                .build();
    }

}
