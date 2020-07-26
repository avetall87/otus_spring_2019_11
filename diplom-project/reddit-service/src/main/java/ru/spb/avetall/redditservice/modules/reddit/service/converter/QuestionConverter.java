package ru.spb.avetall.redditservice.modules.reddit.service.converter;

import ru.spb.avetall.redditservice.modules.question.domain.Question;
import ru.spb.avetall.redditservice.modules.reddit.domain.RedditInfo;

import java.util.List;

public interface QuestionConverter {
    List<Question> convert(RedditInfo redditInfo);
}
