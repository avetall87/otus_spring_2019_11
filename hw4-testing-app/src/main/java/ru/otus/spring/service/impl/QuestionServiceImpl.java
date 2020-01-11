package ru.otus.spring.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.spring.dao.QuestionReader;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionService;

import java.util.List;

@Data
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final String pathToQuestion;
    private final QuestionReader converterService;

    @Override
    public List<Question> getQuestions() {
        return converterService.read(pathToQuestion);
    }

    @Override
    public int getQuestionCount() {
        return converterService.read(pathToQuestion).size();
    }

}
