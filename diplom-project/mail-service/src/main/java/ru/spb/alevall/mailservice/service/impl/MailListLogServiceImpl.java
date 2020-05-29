package ru.spb.alevall.mailservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spb.alevall.common.domain.link.QuestionLink;
import ru.spb.alevall.mailservice.dao.MailListLogDao;
import ru.spb.alevall.mailservice.domain.MailList;
import ru.spb.alevall.mailservice.domain.MailListLog;
import ru.spb.alevall.mailservice.service.MailListLogService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class MailListLogServiceImpl implements MailListLogService {

    private final MailListLogDao mailListLogDao;

    @Override
    @Transactional
    public void save(MailListLog mailListLog) {
        mailListLogDao.save(mailListLog);
    }

    @Override
    public List<QuestionLink> getNonProcessedQuestionLinks(List<QuestionLink> questionLinks, MailList mailList) {
        if (isNull(mailList)) {
            return Collections.emptyList();
        }

        List<QuestionLink> result = new ArrayList<>();

        List<String> allForCurrentDay = mailListLogDao.findAllForCurrentDay(mailList.getId())
                                                      .stream()
                                                      .map(MailListLog::getUrl)
                                                      .collect(Collectors.toList());

        for (QuestionLink questionLink : questionLinks) {
            if (!allForCurrentDay.contains(questionLink.getUrl())) {
                result.add(questionLink);
            }
        }

        return result;
    }
}
