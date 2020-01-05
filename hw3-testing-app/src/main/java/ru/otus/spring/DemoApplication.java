package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.service.QuestionTerminal;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		QuestionTerminal questionTerminal = context.getBean(QuestionTerminal.class);
		questionTerminal.star(new Scanner(System.in));
		context.close();
	}
}
