package ru.otus.spring.dto;

import lombok.Data;

@Data
public class QuestionDto {
    private String question;
    private String[] answers;
    private String correctAnswer;
}
