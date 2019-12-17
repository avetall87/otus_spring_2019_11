package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.Assert;
import ru.otus.spring.dao.QuestionReader;
import ru.otus.spring.dao.impl.CsvReader;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.QuestionTerminal;
import ru.otus.spring.service.impl.QuestionServiceImpl;
import ru.otus.spring.service.impl.ScannerQuestionTerminalImpl;

import java.util.Locale;

import static org.springframework.util.StringUtils.isEmpty;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class AppConfig {

    private final String pathToQuestion;
    private final Locale defaultLocale;
    private final Integer numberOfCorrectAnswers;

    public AppConfig(@Value("${path.to.question}") String pathToQuestion,
                     @Value("${application.locale}") String defaultLocale,
                     @Value("${number.correct.answers}") Integer numberOfCorrectAnswers) {
        this.pathToQuestion = pathToQuestion;
        this.defaultLocale = new Locale(defaultLocale);
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("message");
        messageSource.setDefaultEncoding("UTF-8"); //кодировка в которой будет читаться property файл
        return messageSource;
    }

    // Установка дефолтной локали в приложении !!!
    @Bean
    public MessageSourceAccessor getMessageSourceAccessor(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource, defaultLocale);
    }

    @Bean
    public QuestionService questionService(QuestionReader questionReader) {
        if (isEmpty(pathToQuestion)) {
            throw new IllegalArgumentException("Не указан путь к файлу с вопросами !");
        }
        return new QuestionServiceImpl(pathToQuestion, questionReader);
    }

    @Bean
    public QuestionReader questionReader() {
        return new CsvReader();
    }

    @Bean
    public QuestionTerminal questionTerminal(QuestionService questionService, MessageSourceAccessor messageSourceAccessor) {
        checkQuestionsConfig(questionService.getQuestionCount());
        return new ScannerQuestionTerminalImpl(questionService, messageSourceAccessor, numberOfCorrectAnswers);
    }

    // в проверке будет обращение к сервису вопросов который попробует загрузить список вопросов и проверить их (тут есть оверхед из-за получения списка вопросов - но если ничего не загрузиться то и запуск приложения будет не актуален !)
    private void checkQuestionsConfig(int questionCount) {
        Assert.notNull(numberOfCorrectAnswers, "Не задано кол-во правельных ответов");

        if (questionCount > 0) {
            if (numberOfCorrectAnswers <= 0) {
                throw new IllegalArgumentException("Кол-во правельных ответов должно быть больше 0");
            }

            if (questionCount < numberOfCorrectAnswers) {
                throw new IllegalArgumentException("Кол-во правельных ответов превышает общее вол-во вопросов");
            }
        } else {
            throw new RuntimeException("Не удалось загрузить вопросы для тестирования или файл с вопросами не заполнен");
        }
    }
}