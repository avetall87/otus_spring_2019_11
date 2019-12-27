package ru.otus.spring.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Класс ScannerQuestionTerminalImpl")
class ScannerQuestionTerminalImplTest {

    private static final String TESTING_FIELD_NAME = "correctAnswerCount";

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
    @DisplayName("Правельный ответ")
    void starCorrectAnswer() throws NoSuchFieldException, IllegalAccessException {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("да");
        when(scanner.nextInt()).thenReturn(1);

        ScannerQuestionTerminalImpl questionTerminal = new ScannerQuestionTerminalImpl(questionService, null, 1);
        Field field = questionTerminal.getClass().getDeclaredField(TESTING_FIELD_NAME);
        field.setAccessible(true);

        questionTerminal.star(scanner);

        assertEquals(1, field.getInt(questionTerminal));
    }

    @Test
    @DisplayName("Не правельный ответ")
    void starWrongAnswer() throws NoSuchFieldException, IllegalAccessException {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("да");
        when(scanner.nextInt()).thenReturn(2);

        ScannerQuestionTerminalImpl questionTerminal = new ScannerQuestionTerminalImpl(questionService, null, 1);
        Field field = questionTerminal.getClass().getDeclaredField(TESTING_FIELD_NAME);
        field.setAccessible(true);

        questionTerminal.star(scanner);

        assertEquals(0, field.get(questionTerminal));
    }
}