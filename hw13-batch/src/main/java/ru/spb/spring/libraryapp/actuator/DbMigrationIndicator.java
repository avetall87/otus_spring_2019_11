package ru.spb.spring.libraryapp.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbMigrationIndicator implements HealthIndicator {

    private final MongoTemplate mongoTemplate;
    @Override
    public Health health() {
        Query query = new Query();
        query.addCriteria(Criteria.where("author").exists(true));

        long count = mongoTemplate.count(query, "mongockChangeLog");

        boolean isMigrationReady = count > 0;

        if (!isMigrationReady) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Миграции не установлены !")
                    .build();
        } else {
            return Health.up().withDetail("message", "Миграции успешно установлены").build();
        }
    }
}
