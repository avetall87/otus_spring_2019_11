package ru.otus.spring.service;

import lombok.Data;
import ru.otus.spring.domain.Question;

import java.util.Collections;
import java.util.List;

@Data
public class QuestionServiceImpl implements QuestionService {

    private String pathToQuestion;

    @Override
    public List<Question> getQuestions() {
        return Collections.emptyList();
    }
}
