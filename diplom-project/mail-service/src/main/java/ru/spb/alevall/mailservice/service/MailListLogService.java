package ru.spb.alevall.mailservice.service;

import ru.spb.alevall.common.domain.link.QuestionLink;
import ru.spb.alevall.mailservice.domain.MailList;
import ru.spb.alevall.mailservice.domain.MailListLog;

import java.util.List;

public interface MailListLogService {
    void save(MailListLog mailListLog);
    List<QuestionLink> getNonProcessedQuestionLinks(List<QuestionLink> questionLinks, MailList mailList);
}
