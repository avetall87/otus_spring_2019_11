package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Answer {
    private Integer number;
    private String text;
    private boolean isCorrect;
}