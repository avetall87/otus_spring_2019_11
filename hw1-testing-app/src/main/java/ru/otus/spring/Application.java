package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Ссылка на сайт https://www.baeldung.com/spring-app-setup-with-csv-files как работать с csv !!!
 */
public class Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

//        context.close();
    }
}
