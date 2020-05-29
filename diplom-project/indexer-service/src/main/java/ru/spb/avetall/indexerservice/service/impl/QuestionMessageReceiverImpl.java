package ru.spb.avetall.indexerservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Service;
import ru.spb.avetall.common.domain.link.QuestionLink;
import ru.spb.avetall.common.domain.token.SearchToken;
import ru.spb.avetall.indexerservice.service.IndexDataService;
import ru.spb.avetall.indexerservice.service.MessageReceiver;

import static java.util.Objects.nonNull;

@Slf4j
@Service("questionMessageReceiver")
@RequiredArgsConstructor
public class QuestionMessageReceiverImpl implements MessageReceiver {

    private final IndexDataService indexDataService;

    @Override
    @SuppressWarnings("unchecked")
    public void receive(Message<?> message) {
        Message<QuestionLink> QuestionLinkMessage = (Message<QuestionLink>) message;

        if (nonNull(QuestionLinkMessage)) {
            indexDataService.saveQuestionData(QuestionLinkMessage.getPayload());
            log.info("QuestionLink - {} was successfully save to index", QuestionLinkMessage.getPayload());
        }
    }
}
