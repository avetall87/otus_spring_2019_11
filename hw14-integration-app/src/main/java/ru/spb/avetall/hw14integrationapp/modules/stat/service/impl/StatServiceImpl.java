package ru.spb.avetall.hw14integrationapp.modules.stat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import ru.spb.avetall.hw14integrationapp.modules.question.domain.Question;
import ru.spb.avetall.hw14integrationapp.modules.question.service.QuestionService;
import ru.spb.avetall.hw14integrationapp.modules.stat.service.StatService;

import javax.annotation.PostConstruct;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final long QUEUE_CHANNEL_RECEIVE_TIMEOUT = 1000L;
    private final QueueChannel queueChannel;

    private final QuestionService questionService;

    @PostConstruct
    public void init() {
        processQuestion();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void processQuestion() {
        new Thread(() -> {
            while (true) {
                try {
                    Message<Question> message = (Message<Question>) queueChannel.receive(QUEUE_CHANNEL_RECEIVE_TIMEOUT);
                    if (nonNull(message)) {
                        questionService.save(message.getPayload());
                    }
                } catch (Exception ex) {
                    log.error("Process queue exception", ex);
                }
            }
        }).start();
    }
}
