package ru.spb.avetall.hw14integrationapp.modules.question.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spb.avetall.hw14integrationapp.modules.question.dao.QuestionDao;
import ru.spb.avetall.hw14integrationapp.modules.question.dao.TokenDao;
import ru.spb.avetall.hw14integrationapp.modules.question.domain.Question;
import ru.spb.avetall.hw14integrationapp.modules.question.domain.Token;
import ru.spb.avetall.hw14integrationapp.modules.question.service.QuestionService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;
    private final TokenDao tokenDao;

    @Override
    public void save(Question question) {
        if (nonNull(question) && nonNull(question.getToken())) {

            Token tokenByName = tokenDao.findByTokenName(question.getToken().getTokenName());

            if (isNull(tokenByName)) {
                Token savedToken = question.getToken();
                tokenDao.save(savedToken);

                question.setToken(savedToken);
                questionDao.save(question);
                log.info("Question and token was successfully saved - {}", question);
            } else {

                question.setToken(tokenByName);
                if (questionDao.checkQuestionCountByAuthorFullNameAndTitleAndUrlAndCreationDate(question.getAuthorFullName(),
                                                                                                question.getTitle(),
                                                                                                question.getUrl(),
                                                                                                question.getCreationDate(),
                                                                                                tokenByName.getId()) == 0) {

                    questionDao.save(question);
                    log.info("Question was successfully saved - {}", question);
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findByToken(String token) {
        return questionDao.findByTokenName(token);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findAll() {
        return questionDao.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        questionDao.deleteById(id);
    }
}
