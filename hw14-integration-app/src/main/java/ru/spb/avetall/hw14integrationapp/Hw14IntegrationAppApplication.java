package ru.spb.avetall.hw14integrationapp;

import lombok.SneakyThrows;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;

@EnableScheduling
@EnableIntegration
@SpringBootApplication
@IntegrationComponentScan
@EnableTransactionManagement
public class Hw14IntegrationAppApplication {

    @SneakyThrows(SQLException.class)
    public static void main(String[] args) {
        SpringApplication.run(Hw14IntegrationAppApplication.class, args);

        Console.main(args);
    }

}
