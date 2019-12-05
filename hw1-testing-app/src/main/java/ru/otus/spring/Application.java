package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.QuestionTerminal;

import java.util.Scanner;

/**
 * Ссылка на сайт https://www.baeldung.com/spring-app-setup-with-csv-files как работать с csv !!!
 */
public class Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionTerminal questionTerminal = context.getBean(QuestionTerminal.class);
        questionTerminal.star(new Scanner(System.in));
        context.close();
    }
}
