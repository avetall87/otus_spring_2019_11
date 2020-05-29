package ru.spb.avetall.redditservice.modules.integrationbus;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.spb.avetall.redditservice.modules.question.domain.Question;

@MessagingGateway
public interface IntegrationGateway {
    @Gateway(requestChannel = "redditInfoFlow.input")
    void send(Question questions);
}
