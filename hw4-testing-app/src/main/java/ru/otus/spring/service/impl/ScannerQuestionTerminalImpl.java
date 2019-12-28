package ru.otus.spring.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
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

    private final QuestionService questionService;
    private final MessageSourceAccessor messageSourceAccessor;
    private final Integer numberOFCorrectAnswers;

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
            System.out.println(messageSourceAccessor.getMessage("message.welcome"));
            if (isContinueTesting(scanner.nextLine())) {
                questions.forEach(question -> askQuestion(question, scanner));
                printResult();
            }
        } catch (Exception ex) {
            log.error(messageSourceAccessor.getMessage("system.testing.process.exception"), ex);
        }
    }

    private void printResult() {
        System.out.println(messageSourceAccessor.getMessage("message.total.answers", new Object[]{correctAnswerCount, totalQuestionsCount}));

        if (correctAnswerCount >= numberOFCorrectAnswers) {
            System.out.println(messageSourceAccessor.getMessage("message.result.success"));
        } else {
            System.out.println(messageSourceAccessor.getMessage("message.result.fail", new Object[]{numberOFCorrectAnswers}));
        }
    }

    private void askQuestion(Question question, Scanner scanner) {
        System.out.println();
        System.out.println(messageSourceAccessor.getMessage("message.question.number", new Object[]{question.getQuestion()}));
        System.out.println(messageSourceAccessor.getMessage("message.question.correct.number"));

        for (int i = 0; i < question.getAnswers().size(); i++) {
            System.out.println(getQuestionNumber(i) + ". " + question.getAnswers().get(i).getAnswer());
        }

        int userAnswerNumber = getUserAnswerNumber(scanner);

        if (userAnswerNumber > 0) {
            checkAnswer(question, userAnswerNumber);
        }
    }

    private int getUserAnswerNumber(Scanner scanner) {
        String line = scanner.nextLine();

        if (!line.matches("\\d+")) {
            return 0;
        }

        return Integer.parseInt(line);
    }

    private void checkAnswer(Question question, int userAnswerNumber) {
        if (question.getAnswers().get(userAnswerNumber - 1).isCorrect()) {
            correctAnswerCount++;
        }
    }

    private int getQuestionNumber(int i) {
        return i + 1;
    }

    private boolean isContinueTesting(String userAnswer) {
        return messageSourceAccessor.getMessage("user.answer.continue").equalsIgnoreCase(userAnswer);
    }
}
