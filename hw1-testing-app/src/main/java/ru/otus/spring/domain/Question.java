package ru.otus.spring.domain;

import lombok.Data;
import java.util.List;

@Data
public class Question {
    private Integer number;
    private String text;
    private List<Answer> answers;
}
