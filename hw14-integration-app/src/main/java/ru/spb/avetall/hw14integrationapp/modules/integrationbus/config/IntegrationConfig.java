package ru.spb.avetall.hw14integrationapp.modules.integrationbus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import ru.spb.avetall.hw14integrationapp.modules.question.domain.Question;
import ru.spb.avetall.hw14integrationapp.modules.integrationbus.service.fiter.RedditIntegrationFlowFilter;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class IntegrationConfig {

    @Value("${integration.bus.queue.size:#{1000}}")
    private Integer QUEUE_CHANNEL_SIZE;

    private final RedditIntegrationFlowFilter integrationFlowFilter;

    @Bean
    public IntegrationFlow redditInfoFlow() {
        return IntegrationFlows.from("redditInfoFlow.input")
                .filter((MessageSelector) Objects::nonNull)
                .filter((MessageSelector) message -> integrationFlowFilter.checkRedditPost((Question) message.getPayload()))
                .channel("queueRedditChannel")
                .get();
    }

    @Bean
    public QueueChannel queueRedditChannel() {
        return new QueueChannel(QUEUE_CHANNEL_SIZE);
    }

}
