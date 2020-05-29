package ru.spb.avetall.redditservice.modules.question.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spb.avetall.redditservice.modules.question.dao.TokenDao;
import ru.spb.avetall.redditservice.modules.question.domain.Token;
import ru.spb.avetall.redditservice.modules.question.service.TokenService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenDao tokenDao;

    @Override
    @Transactional
    public void save(Token token) {
        tokenDao.save(token);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Token> findAll() {
        return tokenDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Token> findAllNotSendToIndex() {
        return tokenDao.findAllNotSendToIndex();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        tokenDao.deleteById(id);
    }

    @Override
    @Transactional
    public void setSentToIndex(Boolean sign, Long id) {
        tokenDao.setSentToIndex(sign, id);
    }
}
