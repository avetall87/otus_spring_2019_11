package ru.otus.spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import ru.otus.spring.TestAppContextRunner;
import ru.otus.spring.service.QuestionTerminal;

import java.util.Scanner;

import static org.mockito.Mockito.when;

class ShellQuestionControllerTest extends TestAppContextRunner {

    @Autowired
    private QuestionTerminal questionTerminal;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    private ShellQuestionController shellQuestionController;

    @BeforeEach
    void setUp() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("да","1");
        shellQuestionController = new ShellQuestionController(questionTerminal, messageSourceAccessor, scanner);
    }

    @Test
    void star() {
        shellQuestionController.star();
    }

    @Test
    void help() {
        shellQuestionController.help();
    }
}