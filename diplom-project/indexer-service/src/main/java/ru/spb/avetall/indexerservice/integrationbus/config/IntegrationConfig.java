package ru.spb.avetall.indexerservice.integrationbus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.SubscribableChannel;
import ru.spb.avetall.indexerservice.service.MessageReceiver;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class IntegrationConfig {

    private final @Qualifier("tokenMessageReceiver") MessageReceiver tokenMessageReceiver;
    private final @Qualifier("questionMessageReceiver") MessageReceiver questionMessageReceiver;

    @Bean
    public IntegrationFlow questionFlow() {
        return IntegrationFlows.from("questionFlow.input")
                .filter((MessageSelector) Objects::nonNull)
                .channel("queueQuestionChannel")
                .get();
    }

    @Bean
    public IntegrationFlow tokenFlow() {
        return IntegrationFlows.from("tokenFlow.input")
                .filter((MessageSelector) Objects::nonNull)
                .channel("queueTokenChannel")
                .get();
    }

    @Bean("queueQuestionChannel")
    public SubscribableChannel queueQuestionChannel() {
        PublishSubscribeChannel channel = new PublishSubscribeChannel();
        channel.subscribe(questionMessageReceiver::receive);
        return channel;
    }

    @Bean("queueTokenChannel")
    public SubscribableChannel queueTokenChannel() {
        PublishSubscribeChannel channel = new PublishSubscribeChannel();
        channel.subscribe(tokenMessageReceiver::receive);
        return channel;
    }

}
