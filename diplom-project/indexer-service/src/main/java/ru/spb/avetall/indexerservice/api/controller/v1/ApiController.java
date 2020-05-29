package ru.spb.avetall.indexerservice.api.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spb.avetall.common.domain.link.QuestionLink;
import ru.spb.avetall.indexerservice.service.IndexDataService;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor
public class ApiController {

    private final IndexDataService indexDataService;

    @GetMapping("/all/token")
    public List<String> getTokensFromQuestion() {
        return indexDataService.getTokensFromQuestions();
    }

    @GetMapping("/all/question")
    public List<QuestionLink> getAllQuestion() {
        return indexDataService.findAllQuestionLink();
    }


}
