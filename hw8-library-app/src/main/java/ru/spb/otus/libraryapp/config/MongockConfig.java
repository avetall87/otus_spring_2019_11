package ru.spb.otus.libraryapp.config;

import com.github.cloudyrock.mongock.SpringMongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class MongockConfig {

    private final MongoClient mongoClient;

    @Bean
    public SpringMongock mongock(Environment environment) {
        SpringMongock runner = new SpringMongockBuilder(mongoClient, "library-db", "ru.spb.otus.libraryapp.changelog")
                .setSpringEnvironment(environment)
                .setLockQuickConfig()
                .build();

        return runner;
    }

}
