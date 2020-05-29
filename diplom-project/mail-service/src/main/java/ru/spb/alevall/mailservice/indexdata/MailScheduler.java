package ru.spb.alevall.mailservice.indexdata;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.spb.alevall.common.domain.link.QuestionLink;
import ru.spb.alevall.mailservice.domain.MailList;
import ru.spb.alevall.mailservice.domain.MailListLog;
import ru.spb.alevall.mailservice.indexdata.service.IndexDataApiService;
import ru.spb.alevall.mailservice.service.MailListLogService;
import ru.spb.alevall.mailservice.service.MailListService;
import ru.spb.alevall.mailservice.service.MailTransferService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@Component
@AllArgsConstructor
public class MailScheduler {

    private final IndexDataApiService indexDataApiService;
    private final MailListLogService mailListLogService;
    private final MailTransferService mailTransferService;
    private final MailListService mailListService;

    @Scheduled(fixedDelay = 10_000)
    public void getAndTransferData() {
        try {
            List<QuestionLink> questionLinks = indexDataApiService.receiveData();

            if (isNotEmpty(questionLinks)) {
                mailListService.findAll().forEach(mailList -> {
                    List<QuestionLink> nonProcessedQuestionLinks = mailListLogService.getNonProcessedQuestionLinks(questionLinks, mailList);

                    if (isNotEmpty(nonProcessedQuestionLinks)) {
                        sendByEmail(mailList, nonProcessedQuestionLinks);
                    }
                });
            }
        } catch (Exception ex) {
            log.error("GetAndTransferData exception", ex);
        }
    }

    private void sendByEmail(MailList mailList, List<QuestionLink> questionLinks) {
        try {
            mailTransferService.sendEmail(questionLinks, mailList.getEmail());

            List<MailListLog> mailListLogs = questionLinks.stream()
                                                          .map(questionLink -> toMailListLog(questionLink, mailList))
                                                          .collect(Collectors.toList());

            mailListLogs.forEach(mailListLogService::save);
        } catch (Exception ex) {
          log.error("SendByEmail exception for email={}", mailList.getEmail(), ex);
        }
    }

    private MailListLog toMailListLog(QuestionLink questionLink, MailList mailList) {
        return MailListLog.builder()
                          .url(questionLink.getUrl())
                          .transferDate(LocalDateTime.now())
                          .mailList(mailList)
                          .build();
    }
}
