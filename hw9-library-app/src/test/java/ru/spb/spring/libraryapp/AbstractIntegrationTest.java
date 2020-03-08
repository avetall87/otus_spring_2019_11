package ru.spb.spring.libraryapp;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.spb.spring.libraryapp.config.MongoConfig;
import ru.spb.spring.libraryapp.config.MongockConfig;
import ru.spb.spring.libraryapp.controller.AuthorController;
import ru.spb.spring.libraryapp.controller.BookController;

@DataMongoTest
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "ru.spb.spring.libraryapp",
        excludeFilters = {@ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                value = {MongockConfig.class, MongoConfig.class})
        })
public abstract class AbstractIntegrationTest {

}
