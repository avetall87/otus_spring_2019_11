package ru.spb.spring.libraryapp.config;

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
    private final MongoProperties mongoConfig;

    @Bean
    public SpringMongock mongock(Environment environment) {
        return new SpringMongockBuilder(mongoClient, mongoConfig.getDatabase(), "ru.spb.spring.libraryapp.changelog")
                .setSpringEnvironment(environment)
                .setLockQuickConfig()
                .build();
    }
}
