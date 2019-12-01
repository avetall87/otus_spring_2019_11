package ru.otus.spring.readers;

import ru.otus.spring.domain.Question;

import java.util.List;

public interface QuestionReader {
   List<Question> read(String pathToCsv);
}
