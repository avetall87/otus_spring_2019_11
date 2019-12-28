package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public QuestionService questionService(QuestionReader questionReader, MessageSourceAccessor messageSourceAccessor) {
        if (isEmpty(pathToQuestion)) {
            throw new IllegalArgumentException(messageSourceAccessor.getMessage("system.message.data.file.not.found"));
        }
        return new QuestionServiceImpl(pathToQuestion, questionReader);
    }

    @Bean
    public QuestionReader questionReader() {
        return new CsvReader();
    }

    @Bean
    public QuestionTerminal questionTerminal(QuestionService questionService, MessageSourceAccessor messageSourceAccessor) {
        checkQuestionsConfig(questionService.getQuestionCount(), messageSourceAccessor);
        return new ScannerQuestionTerminalImpl(questionService, messageSourceAccessor, numberOfCorrectAnswers);
    }

    private void checkQuestionsConfig(int questionCount, MessageSourceAccessor messageSourceAccessor) {
        Assert.notNull(numberOfCorrectAnswers, messageSourceAccessor.getMessage("system.message.empty.correct.answer"));

        if (questionCount > 0) {
            if (numberOfCorrectAnswers <= 0) {
                throw new IllegalArgumentException(messageSourceAccessor.getMessage("system.message.correct.answer.count"));
            }

            if (questionCount < numberOfCorrectAnswers) {
                throw new IllegalArgumentException(messageSourceAccessor.getMessage("system.message.many.correct.answer"));
            }
        } else {
            throw new RuntimeException(messageSourceAccessor.getMessage("system.message.fail.load.question"));
        }
    }
}
