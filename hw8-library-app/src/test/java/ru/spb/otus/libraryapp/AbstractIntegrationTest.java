package ru.spb.otus.libraryapp;

import com.mongodb.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;

@DataMongoTest(properties = "/application.yml")
@Testcontainers
@ExtendWith(SpringExtension.class)
public class AbstractIntegrationTest {

    private MongoTemplate mongoTemplate;

    @Container
    public GenericContainer mongo = new GenericContainer("mongo").withExposedPorts(27017);

    @BeforeEach
    void setUp() {
        mongoTemplate = new MongoTemplate(new MongoClient(mongo.getContainerIpAddress(), mongo.getFirstMappedPort()), "test");
    }

    @Test
    public void containerStart() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Steve");
        mongoTemplate.save(map, "someCollection");
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
