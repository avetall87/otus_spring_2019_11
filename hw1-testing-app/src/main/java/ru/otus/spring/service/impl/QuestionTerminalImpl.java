package ru.otus.spring.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.QuestionTerminal;

import java.util.List;
import java.util.Scanner;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Data
@RequiredArgsConstructor
public class QuestionTerminalImpl implements QuestionTerminal {

    private static final String WELCOME = "Здравствуйте, Вы готовы пройти тестирование ? [да/нет]";
    private static final String CONTINUE_TESTING = "да";

    private final QuestionService questionService;

    private int correctAnswerCount;
    private int totalQuestionsCount;

    @Override
    public void star() {
        List<Question> questions = questionService.getQuestions();

        if (isNotEmpty(questions)) {
            totalQuestionsCount = questions.size();
            startTesting(questions);
        }
    }

    private void startTesting(List<Question> questions) {
        try {
            Scanner in = new Scanner(System.in);
            System.out.println(WELCOME);
            if (isContinueTesting(in.nextLine())) {
                questions.forEach(question -> askQuestion(question, in));
                System.out.println("Вы ответили на " + correctAnswerCount + " из " + totalQuestionsCount + " вопросов");
            } else {
                in.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void askQuestion(Question question, Scanner scanner) {
        System.out.println();
        System.out.println("Вопрос: " + question.getQuestion());
        System.out.println("Укажите номер правильного ответа:");

        for (int i = 0; i < question.getAnswers().size(); i++) {
            System.out.println((i + 1) + ". " + question.getAnswers().get(i).getAnswer());
        }

        int userAnswerNumber = scanner.nextInt();

        if (question.getAnswers().get(userAnswerNumber - 1).isCorrect()) {
            correctAnswerCount++;
        }
    }

    private boolean isContinueTesting(String userAnswer) {
        return CONTINUE_TESTING.equalsIgnoreCase(userAnswer);
    }
}
