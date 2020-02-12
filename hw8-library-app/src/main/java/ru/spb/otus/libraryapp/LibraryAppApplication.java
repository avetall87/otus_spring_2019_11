package ru.spb.otus.libraryapp;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryAppApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(LibraryAppApplication.class, args);
    }

}
