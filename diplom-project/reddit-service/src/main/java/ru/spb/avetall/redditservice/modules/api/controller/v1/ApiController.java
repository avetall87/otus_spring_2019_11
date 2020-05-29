package ru.spb.avetall.redditservice.modules.api.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spb.avetall.common.domain.link.QuestionLink;
import ru.spb.avetall.common.domain.token.SearchToken;
import ru.spb.avetall.redditservice.modules.question.domain.Question;
import ru.spb.avetall.redditservice.modules.question.domain.Token;
import ru.spb.avetall.redditservice.modules.question.service.QuestionService;
import ru.spb.avetall.redditservice.modules.question.service.TokenService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static ru.spb.avetall.redditservice.modules.api.service.ApiServiceHelper.*;

@RestController
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor
public class ApiController {

    private final TokenService tokenService;
    private final QuestionService questionService;

    @GetMapping("/all/token")
    public List<SearchToken> getAllSearchTopics() {
        List<Token> tokens = tokenService.findAllNotSendToIndex();

        if (isNotEmpty(tokens)) {
            tokens.stream()
                    .filter(Objects::nonNull)
                    .map(Token::getId)
                    .collect(Collectors.toList())
                    .forEach(id -> tokenService.setSentToIndex(true, id));

            return toSearchTokens(tokens);
        } else {
            return Collections.emptyList();
        }
    }

    @GetMapping("/all/question")
    public List<QuestionLink> getAllQuestionLinks() {
        List<Question> questions = questionService.findAllNotSendToIndex();

        if (isNotEmpty(questions)) {
            questions.stream()
                     .filter(Objects::nonNull)
                     .map(Question::getId)
                     .collect(Collectors.toList())
                     .forEach(id -> questionService.setSentToIndex(true, id));

            return toQuestionLinks(questions);
        } else {
            return Collections.emptyList();
        }
    }
}