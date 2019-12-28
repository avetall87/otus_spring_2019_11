package ru.otus.spring;


import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration(classes = {TestAppConfig.class, TestPropertyConfig.class})
public class TestAppContextRunner {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(context).isNotNull();
        assertions.assertAll();
    }

    public ApplicationContext getContext() {
        return context;
    }

}
