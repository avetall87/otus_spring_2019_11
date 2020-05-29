package ru.spb.avetall.indexerservice.integrationbus;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.spb.avetall.common.domain.link.QuestionLink;
import ru.spb.avetall.common.domain.token.SearchToken;

@MessagingGateway
public interface IntegrationGateway {
    @Gateway(requestChannel = "questionFlow.input")
    void sendQuestionLink(QuestionLink questionLink);

    @Gateway(requestChannel = "tokenFlow.input")
    void sendSearchToken(SearchToken searchToken);
}
