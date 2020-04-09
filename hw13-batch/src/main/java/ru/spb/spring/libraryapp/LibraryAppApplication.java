package ru.spb.spring.libraryapp;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import org.h2.tools.Console;

@SpringBootApplication
@EnableMongoRepositories
public class LibraryAppApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(LibraryAppApplication.class, args);
        Console.main(args);
    }

}
