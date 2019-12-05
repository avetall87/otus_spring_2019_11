package ru.otus.spring.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.QuestionTerminal;

import java.util.List;
import java.util.Scanner;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Data
@Slf4j
@RequiredArgsConstructor
public class ScannerQuestionTerminalImpl implements QuestionTerminal {

    private static final String WELCOME = "Здравствуйте, Вы готовы пройти тестирование ? [да/нет]";
    private static final String CONTINUE_TESTING = "да";

    private final QuestionService questionService;

    private int correctAnswerCount;
    private int totalQuestionsCount;

    @Override
    public void star(Scanner scanner) {
        List<Question> questions = questionService.getQuestions();

        if (isNotEmpty(questions)) {
            totalQuestionsCount = questions.size();
            startTesting(scanner, questions);
        }
    }

    private void startTesting(Scanner scanner, List<Question> questions) {
        try {
            System.out.println(WELCOME);
            if (isContinueTesting(scanner.nextLine())) {
                questions.forEach(question -> askQuestion(question, scanner));
                System.out.println("Вы ответили на " + correctAnswerCount + " из " + totalQuestionsCount + " вопросов");
            }
        } catch (Exception ex) {
            log.error("Testing process exception", ex);
        }
    }

    private void askQuestion(Question question, Scanner scanner) {
        System.out.println();
        System.out.println("Вопрос: " + question.getQuestion());
        System.out.println("Укажите номер правильного ответа:");

        for (int i = 0; i < question.getAnswers().size(); i++) {
            System.out.println(getQuestionNumber(i) + ". " + question.getAnswers().get(i).getAnswer());
        }

        int userAnswerNumber = scanner.nextInt();

        if (question.getAnswers().get(userAnswerNumber - 1).isCorrect()) {
            correctAnswerCount++;
        }
    }

    private int getQuestionNumber(int i) {
        return i + 1;
    }

    private boolean isContinueTesting(String userAnswer) {
        return CONTINUE_TESTING.equalsIgnoreCase(userAnswer);
    }
}
