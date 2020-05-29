package ru.spb.alevall.mailservice.service.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.spb.alevall.mailservice.domain.MailList;
import ru.spb.alevall.mailservice.service.MailListService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailListServiceImplTest {

    @Autowired
    private MailListService mailListService;

    @Test
    void findAll() {
        List<MailList> lists = mailListService.findAll();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(lists).isNotEmpty();
            softAssertions.assertThat(lists.size()).isEqualTo(1);
        });
    }
}