package ru.spb.avetall.redditservice.modules.question.service;

import ru.spb.avetall.redditservice.modules.question.domain.Question;
import ru.spb.avetall.redditservice.modules.question.domain.Token;

import java.util.List;

public interface QuestionService {
    void save(Question question);
    List<Question> findByToken(String token);
    List<Question> findAll();
    List<Question> findAllNotSendToIndex();
    void deleteById(Long id);
    void setSentToIndex(Boolean sign, Long id);
}
