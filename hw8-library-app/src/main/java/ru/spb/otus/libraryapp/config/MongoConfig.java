package ru.spb.otus.libraryapp.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ru.spb.otus.libraryapp.dao")
public class MongoConfig extends AbstractMongoClientConfiguration {

    private final String dbName;
    private final String host;
    private final String port;
    private final String username;
    private final String password;

    public MongoConfig(@Value("${mongodb.database}") String dbName,
                       @Value("${mongodb.host}") String host,
                       @Value("${mongodb.port}") String port,
                       @Value("${mongodb.username}") String username,
                       @Value("${mongodb.password}") String password) {

        this.dbName = dbName;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    @Override
    public String getDatabaseName() {
        return dbName;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://" + username + ":" + password + "@" + host + ":+" + port + "/" + dbName + "?authSource=admin");
    }

    @Bean
    public MongoDbFactory mongoDbFactory(MongoClient mongoClient) {
        return new SimpleMongoClientDbFactory(mongoClient, dbName);
    }
}