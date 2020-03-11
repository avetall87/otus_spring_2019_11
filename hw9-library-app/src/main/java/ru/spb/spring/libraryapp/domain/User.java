package ru.spb.spring.libraryapp.domain;

import lombok.Data;

@Data
public class User {
    private String login;
    private String password;
}
