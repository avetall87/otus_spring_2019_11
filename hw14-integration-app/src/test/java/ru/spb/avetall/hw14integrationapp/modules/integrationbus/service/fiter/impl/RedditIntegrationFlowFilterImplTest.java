package ru.spb.avetall.hw14integrationapp.modules.integrationbus.service.fiter.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.spb.avetall.hw14integrationapp.modules.integrationbus.service.fiter.RedditIntegrationFlowFilter;
import ru.spb.avetall.hw14integrationapp.modules.question.domain.Question;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource("classpath:test-application.yml")
class RedditIntegrationFlowFilterImplTest {

    @Autowired
    private RedditIntegrationFlowFilter redditIntegrationFlowFilter;


    @Test
    void checkRedditPostErrorResultEmptyObject() {
        boolean result = redditIntegrationFlowFilter.checkRedditPost(Question.builder().build());
        assertFalse(result);
    }

    @Test
    void checkRedditPostErrorResultNullObject() {
        boolean result = redditIntegrationFlowFilter.checkRedditPost(null);
        assertFalse(result);
    }

    @Test
    void checkRedditPostErrorResultWrongTitle() {
        boolean result = redditIntegrationFlowFilter.checkRedditPost(Question.builder().title("Title").build());
        assertFalse(result);
    }

    @Test
    void checkRedditPostSuccessResult() {
        boolean result = redditIntegrationFlowFilter.checkRedditPost(Question.builder().title("TEST-TOKEN").build());
        assertTrue(result);
    }
}