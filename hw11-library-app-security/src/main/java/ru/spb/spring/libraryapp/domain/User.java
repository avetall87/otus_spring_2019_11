package ru.spb.spring.libraryapp.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String firstName;
    private String lastName;
    private String patronymic;
    private String password;
}
