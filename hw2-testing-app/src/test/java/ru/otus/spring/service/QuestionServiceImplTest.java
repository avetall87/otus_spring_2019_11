package ru.otus.spring.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.TestAppContextRunner;
import ru.otus.spring.service.impl.QuestionServiceImpl;

@DisplayName("Тест для проверки сервиса вопосов")
class QuestionServiceImplTest extends TestAppContextRunner {

    @Test
    @DisplayName("Проверка инекции в переменную пути до файла с вопрсами")
    void checkInjectPathToQuestionsList() {
        QuestionServiceImpl questionService = getContext().getBean(QuestionServiceImpl.class);

        SoftAssertions.assertSoftly((softAssertions) -> {
            softAssertions.assertThat(questionService).isNotNull();
            softAssertions.assertThat(questionService.getPathToQuestion()).isEqualTo("csv/question.csv");
            softAssertions.assertThat(questionService.getPathToQuestion()).isEqualTo("csv/question.csv");
            softAssertions.assertAll();
        });
    }


}