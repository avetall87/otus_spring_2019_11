package ru.spb.avetall.indexerservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import ru.spb.avetall.common.domain.token.SearchToken;
import ru.spb.avetall.indexerservice.service.IndexDataService;
import ru.spb.avetall.indexerservice.service.MessageReceiver;

import static java.util.Objects.nonNull;

@Slf4j
@Service("tokenMessageReceiver")
@RequiredArgsConstructor
public class TokenMessageReceiverImpl implements MessageReceiver {

    private final IndexDataService indexDataService;

    @Override
    @SuppressWarnings("unchecked")
    public void receive(Message<?> message) {
        Message<SearchToken> searchTokenMessage = (Message<SearchToken>) message;

        if (nonNull(searchTokenMessage)) {
            indexDataService.saveTokenData(searchTokenMessage.getPayload());
            log.info("SearchToken - {} was successfully save to index", searchTokenMessage.getPayload());
        }
    }
}
