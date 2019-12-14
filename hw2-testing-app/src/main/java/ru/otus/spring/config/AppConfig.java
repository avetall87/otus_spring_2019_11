package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.dao.QuestionReader;
import ru.otus.spring.dao.impl.CsvReader;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.QuestionTerminal;
import ru.otus.spring.service.impl.QuestionServiceImpl;
import ru.otus.spring.service.impl.ScannerQuestionTerminalImpl;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class AppConfig {

    @Value("${path.to.question}")
    private String pathToQuestion;

    @Bean
    public QuestionService questionService(QuestionReader questionReader) {
        return new QuestionServiceImpl(pathToQuestion, questionReader);
    }

    @Bean
    public QuestionReader questionReader() {
        return new CsvReader();
    }

    @Bean
    public QuestionTerminal questionTerminal(QuestionService questionService) {
        return new ScannerQuestionTerminalImpl(questionService);
    }

}
