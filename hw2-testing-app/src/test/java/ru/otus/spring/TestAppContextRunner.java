package ru.otus.spring;


import org.junit.jupiter.api.BeforeAll;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@ComponentScan
@ContextConfiguration(classes = {TestAppConfig.class, TestPropertyConfig.class})
@TestPropertySource
public class TestAppContextRunner {

    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    static void setUp() {
        context = new AnnotationConfigApplicationContext(TestAppConfig.class, TestPropertyConfig.class);
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
    }

}
