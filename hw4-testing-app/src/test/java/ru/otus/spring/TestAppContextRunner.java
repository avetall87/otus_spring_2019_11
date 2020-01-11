package ru.otus.spring;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import ru.otus.spring.controller.ShellQuestionController;

@SpringBootTest
@ContextHierarchy(
        @ContextConfiguration(classes = {TestAppConfig.class, TestPropertyConfig.class})
)
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ShellQuestionController.class))
@DisplayName("Класс для загрухзки тестового контекста")
public class TestAppContextRunner {

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("Проверка загрузки тестового контекста")
    void contextLoads() {
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(context).isNotNull();
        assertions.assertAll();
    }

    public ApplicationContext getContext() {
        return context;
    }

}
