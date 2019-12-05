package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.util.List;

public interface TestingService {
    void runTesting(List<Question> questions);
}
