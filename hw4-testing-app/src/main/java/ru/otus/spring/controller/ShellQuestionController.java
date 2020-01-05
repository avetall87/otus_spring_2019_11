package ru.otus.spring.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.QuestionTerminal;

import java.util.Scanner;

@Data
@ShellComponent
@RequiredArgsConstructor
public class ShellQuestionController {

    private final QuestionTerminal questionTerminal;
    private final MessageSourceAccessor messageSourceAccessor;
    private final Scanner scanner;

    @ShellMethod(value = "start", key = {"start", "старт"})
    public void star() {
        questionTerminal.star(scanner);
    }

    @ShellMethod(value = "help", key = "h")
    public void help() {
        System.out.println(messageSourceAccessor.getMessage("message.help"));
    }

}
