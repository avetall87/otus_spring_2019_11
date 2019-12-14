package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.service.QuestionTerminal;

import java.util.Scanner;

@ComponentScan(basePackages = "ru.otus.spring")
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        QuestionTerminal questionTerminal = context.getBean(QuestionTerminal.class);
        questionTerminal.star(new Scanner(System.in));
        context.close();
    }
}
