package ru.spb.spring.libraryapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("spring.data.mongodb")
public class MongoProperties {
    private String uri;
    private String port;
    private String database;
    private String username;
    private String password;
}
