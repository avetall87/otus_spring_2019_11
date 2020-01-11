package ru.otus.spring.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import ru.otus.spring.TestAppContextRunner;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Класс ScannerQuestionTerminalImpl")
class ScannerQuestionTerminalImplTest extends TestAppContextRunner {

    private static final String TESTING_FIELD_NAME = "correctAnswerCount";

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        questionService = Mockito.mock(QuestionServiceImpl.class);
        when(questionService.getQuestions()).thenReturn(getStubQuestionData());

    }

    private List<Question> getStubQuestionData() {
        return List.of(Question.builder()
                .question("Вопрос")
                .answers(List.of(
                        Answer.builder().answer("ответ1").isCorrect(true).build(),
                        Answer.builder().answer("ответ2").isCorrect(false).build())
                )
                .build());
    }

    @Test
    @DisplayName("Правильный ответ")
    void starCorrectAnswer() throws NoSuchFieldException, IllegalAccessException {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("да","1");

        ScannerQuestionTerminalImpl questionTerminal = new ScannerQuestionTerminalImpl(questionService, messageSourceAccessor, 1);
        Field field = questionTerminal.getClass().getDeclaredField(TESTING_FIELD_NAME);
        field.setAccessible(true);

        questionTerminal.star(scanner);

        assertEquals(1, field.getInt(questionTerminal));
    }

    @Test
    @DisplayName("Не правильный ответ")
    void starWrongAnswer() throws NoSuchFieldException, IllegalAccessException {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("да", "2");

        ScannerQuestionTerminalImpl questionTerminal = new ScannerQuestionTerminalImpl(questionService, messageSourceAccessor, 1);
        Field field = questionTerminal.getClass().getDeclaredField(TESTING_FIELD_NAME);
        field.setAccessible(true);

        questionTerminal.star(scanner);

        assertEquals(0, field.get(questionTerminal));
    }
}