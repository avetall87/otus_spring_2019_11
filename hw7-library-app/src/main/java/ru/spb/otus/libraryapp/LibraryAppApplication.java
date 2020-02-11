package ru.spb.otus.libraryapp;

import lombok.SneakyThrows;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class LibraryAppApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(LibraryAppApplication.class, args);
        Console.main(args);
    }

}
