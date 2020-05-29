package ru.spb.alevall.mailservice.service.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.spb.alevall.common.domain.link.QuestionLink;
import ru.spb.alevall.mailservice.dao.MailListDao;
import ru.spb.alevall.mailservice.dao.MailListLogDao;
import ru.spb.alevall.mailservice.domain.MailList;
import ru.spb.alevall.mailservice.domain.MailListLog;
import ru.spb.alevall.mailservice.service.MailListLogService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class MailListLogServiceImplTest {

    @Autowired
    private MailListLogService mailListLogService;

    @Autowired
    private MailListLogDao mailListLogDao;

    @Autowired
    private MailListDao mailListDao;

    @Test
    void save() {
        mailListLogService.save(
                MailListLog.builder()
                        .mailList(MailList.builder()
                                .id(1L)
                                .build())
                        .url("url")
                        .transferDate(LocalDateTime.now())
                        .build());

        List<MailListLog> all = mailListLogDao.findAll();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(all).isNotEmpty();
            softAssertions.assertThat(all.size()).isEqualTo(1);
            softAssertions.assertThat(all.get(0).getMailList().getId()).isEqualTo(1);
            softAssertions.assertThat(all.get(0).getUrl()).isEqualTo("url");
            softAssertions.assertThat(all.get(0).getTransferDate().toLocalDate()).isEqualTo(LocalDate.now());
        });
    }

    @Test
    void getNonProcessedQuestionLinks() {
        mailListLogDao.save(
                MailListLog.builder()
                        .mailList(MailList.builder()
                                .id(1L)
                                .build())
                        .url("url_2")
                        .transferDate(LocalDateTime.now().minusDays(1))
                        .build());

        List<QuestionLink> questionLinks = List.of(QuestionLink.builder()
                                                               .creationDate(LocalDateTime.now())
                                                               .question("test")
                                                               .url("test")
                                                               .token("test")
                                                               .build());

        List<QuestionLink> nonProcessedQuestionLinks = mailListLogService.getNonProcessedQuestionLinks(questionLinks, mailListDao.findById(1L)
                                                                                                                                 .orElse(null));

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(nonProcessedQuestionLinks).isNotEmpty();
            softAssertions.assertThat(nonProcessedQuestionLinks.size()).isEqualTo(1);
            softAssertions.assertThat(nonProcessedQuestionLinks.get(0).getQuestion()).isEqualTo("test");
        });
    }

    @Test
    void getNonProcessedQuestionLinksFirstStageNoLogs() {
        mailListLogDao.deleteAll();

        List<QuestionLink> questionLinks = List.of(QuestionLink.builder()
                .creationDate(LocalDateTime.now())
                .question("test")
                .url("test")
                .token("test")
                .build());

        List<QuestionLink> nonProcessedQuestionLinks = mailListLogService.getNonProcessedQuestionLinks(questionLinks, mailListDao.findById(1L)
                .orElse(null));

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(nonProcessedQuestionLinks).isNotEmpty();
            softAssertions.assertThat(nonProcessedQuestionLinks.size()).isEqualTo(1);
            softAssertions.assertThat(nonProcessedQuestionLinks.get(0).getQuestion()).isEqualTo("test");
        });
    }
}