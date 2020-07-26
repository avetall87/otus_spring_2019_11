package ru.spb.avetall.redditservice.modules.api.service.converter;

import ru.spb.avetall.common.domain.link.QuestionLink;
import ru.spb.avetall.common.domain.token.SearchToken;
import ru.spb.avetall.redditservice.modules.question.domain.Question;
import ru.spb.avetall.redditservice.modules.question.domain.Token;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public final class ApiConverter {

    public static SearchToken toSearchToken(Token token) {
        if (isNull(token)) {
            return null;
        }

        return SearchToken
                .builder()
                .name(token.getTokenName())
                .build();
    }

    public static QuestionLink toQuestionLink(Question question) {
        if (isNull(question)) {
            return null;
        }

        String tokenName = (nonNull(question.getToken())) ? question.getToken().getTokenName() : "";

        return QuestionLink
                .builder()
                .token(tokenName)
                .url(question.getUrl())
                .question(question.getTitle())
                .creationDate(question.getCreationDate())
                .build();
    }

}
