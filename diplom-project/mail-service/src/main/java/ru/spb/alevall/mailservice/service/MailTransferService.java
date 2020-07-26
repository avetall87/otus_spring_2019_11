package ru.spb.alevall.mailservice.service;

import ru.spb.alevall.common.domain.link.QuestionLink;

import java.util.List;

public interface MailTransferService {
    void sendEmail(List<QuestionLink> questionLinks, String mailToSent);
}
