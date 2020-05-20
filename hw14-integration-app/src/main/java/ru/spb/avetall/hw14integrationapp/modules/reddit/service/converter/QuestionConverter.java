package ru.spb.avetall.hw14integrationapp.modules.reddit.service.converter;

import ru.spb.avetall.hw14integrationapp.modules.question.domain.Question;
import ru.spb.avetall.hw14integrationapp.modules.reddit.domain.RedditInfo;

import java.util.List;

public interface QuestionConverter {
    List<Question> convert(RedditInfo redditInfo);
}
