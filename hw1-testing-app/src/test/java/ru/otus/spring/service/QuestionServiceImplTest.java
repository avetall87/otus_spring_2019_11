package ru.otus.spring.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.impl.QuestionServiceImpl;

@DisplayName("Тест для проверки сервиса вопосов")
class QuestionServiceImplTest {

    private static ClassPathXmlApplicationContext context;

    @BeforeAll
    static void setUp() {
        context = new ClassPathXmlApplicationContext("/spring-context-path_to_questions.xml");
    }

    @Test
    @DisplayName("Проверка инекции в переменную пути до файла с вопрсами")
    void checkInjectPathToQuestionsList() {
        QuestionServiceImpl questionService = context.getBean(QuestionServiceImpl.class);

        SoftAssertions.assertSoftly((softAssertions) -> {
            softAssertions.assertThat(questionService).isNotNull();
            softAssertions.assertThat(questionService.getPathToQuestion()).isEqualTo("somePath");
            softAssertions.assertAll();
        });
    }






}