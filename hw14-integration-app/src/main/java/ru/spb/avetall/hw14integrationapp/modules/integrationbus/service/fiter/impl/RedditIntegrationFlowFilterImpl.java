package ru.spb.avetall.hw14integrationapp.modules.integrationbus.service.fiter.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.spb.avetall.hw14integrationapp.modules.question.domain.Question;
import ru.spb.avetall.hw14integrationapp.modules.integrationbus.service.fiter.RedditIntegrationFlowFilter;
import ru.spb.avetall.hw14integrationapp.modules.question.domain.Token;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Slf4j
@Service
public class RedditIntegrationFlowFilterImpl implements RedditIntegrationFlowFilter {

    @Value("${reddit.filter.token}")
    private String filterToken;

    private final AtomicReference<LocalDateTime> lastUpdateDate = new AtomicReference<>(LocalDateTime.now());

    @Override
    public boolean checkRedditPost(Question question) {
        if (isNull(question) || isNull(question.getTitle())) {
            return false;
        }

        if (isBlank(filterToken)) {
            throw new IllegalStateException("Reddit filter token is blank !");
        }

        question.setToken(Token.builder().tokenName(filterToken).build());

        if (question.getTitle().toLowerCase().contains(filterToken.toLowerCase().trim())) {
            lastUpdateDate.set(question.getUpdateDate());
            return true;
        } else {
            return false;
        }
    }
}
