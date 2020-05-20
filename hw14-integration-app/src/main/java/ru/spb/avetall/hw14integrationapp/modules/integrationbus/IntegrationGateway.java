package ru.spb.avetall.hw14integrationapp.modules.integrationbus;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.spb.avetall.hw14integrationapp.modules.question.domain.Question;

@MessagingGateway
public interface IntegrationGateway {
    @Gateway(requestChannel = "redditInfoFlow.input")
    void send(Question questions);
}
