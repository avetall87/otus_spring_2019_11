package ru.spb.alevall.mailservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.spb.alevall.common.domain.link.QuestionLink;
import ru.spb.alevall.mailservice.service.MailTransferService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailTransferServiceImpl implements MailTransferService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(List<QuestionLink> questionLinks, String mailToSent) {
        if (!isEmpty(mailToSent)) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Бот рассылки за - " + getLocalDateTimeNowAsString());
            message.setTo(mailToSent);
            message.setText(getMessageBodyAsText(questionLinks));

            javaMailSender.send(message);
        }
    }

    private String getMessageBodyAsText(List<QuestionLink> questionLinks) {

        StringJoiner link = new StringJoiner("\n\n");

        link.add("Привет, новая рассылка !");

        questionLinks.forEach(questionLink -> {
            String messageLink = MessageLink.builder()
                                            .question(questionLink.getQuestion())
                                            .url(questionLink.getUrl())
                                            .creationDate(questionLink.getCreationDate())
                                            .build()
                                            .toString();
            link.add(messageLink);

        });

        return link.toString();
    }

    private String getLocalDateTimeNowAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    @Data
    @Builder
    @AllArgsConstructor
    private static class MessageLink {
        private String question;
        private String url;
        private LocalDateTime creationDate;

        public String toString() {
            StringJoiner joiner = new StringJoiner("\n");
            joiner.add("Вопрос: " + question);
            joiner.add("Ссылка: " + url);
            joiner.add("Дата вопрса: " + creationDate);
            return joiner.toString();
        }
    }
}
