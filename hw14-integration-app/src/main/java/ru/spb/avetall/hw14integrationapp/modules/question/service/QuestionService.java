package ru.spb.avetall.hw14integrationapp.modules.question.service;

import ru.spb.avetall.hw14integrationapp.modules.question.domain.Question;

import java.util.List;

public interface QuestionService {
    void save(Question question);
    List<Question> findByToken(String token);
    List<Question> findAll();
    void deleteById(Long id);
}
