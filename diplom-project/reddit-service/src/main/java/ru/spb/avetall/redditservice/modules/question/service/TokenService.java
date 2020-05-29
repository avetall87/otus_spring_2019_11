package ru.spb.avetall.redditservice.modules.question.service;

import ru.spb.avetall.redditservice.modules.question.domain.Token;

import java.util.List;

public interface TokenService {
    void save(Token token);
    List<Token> findAll();
    List<Token> findAllNotSendToIndex();
    void deleteById(Long id);
    void setSentToIndex(Boolean sign, Long id);
}
