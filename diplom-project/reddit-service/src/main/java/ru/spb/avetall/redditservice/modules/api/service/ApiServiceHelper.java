package ru.spb.avetall.redditservice.modules.api.service;

import lombok.RequiredArgsConstructor;
import ru.spb.avetall.common.domain.link.QuestionLink;
import ru.spb.avetall.common.domain.token.SearchToken;
import ru.spb.avetall.redditservice.modules.api.service.converter.ApiConverter;
import ru.spb.avetall.redditservice.modules.question.domain.Question;
import ru.spb.avetall.redditservice.modules.question.domain.Token;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@RequiredArgsConstructor
public class ApiServiceHelper {

    public static List<SearchToken> toSearchTokens(List<Token> tokens) {
        if (!isNotEmpty(tokens)) {
            return Collections.emptyList();
        }

        return tokens
                .stream()
                .filter(Objects::nonNull)
                .map(ApiConverter::toSearchToken)
                .collect(Collectors.toList());
    }

    public static List<QuestionLink> toQuestionLinks(List<Question> questions) {
        if (!isNotEmpty(questions)) {
            return Collections.emptyList();
        }

        return questions
                .stream()
                .filter(Objects::nonNull)
                .map(ApiConverter::toQuestionLink)
                .collect(Collectors.toList());
    }
}