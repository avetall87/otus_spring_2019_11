package ru.otus.spring.mappers;

import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.dto.QuestionDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionMapper {

    public static Question map(QuestionDto dto) {
        List<Answer> answers = new ArrayList<>();
        Arrays.stream(dto.getAnswers()).forEach(answer -> answers.add(Answer.builder().answer(answer).isCorrect(answer.equalsIgnoreCase(dto.getCorrectAnswer())).build()));

        return Question.builder()
                .question(dto.getQuestion())
                .answers(answers)
                .build();
    }

}
