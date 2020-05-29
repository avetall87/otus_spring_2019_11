package ru.spb.alevall.mailservice.service;

import ru.spb.alevall.mailservice.domain.MailList;

import java.util.List;

public interface MailListService {
    List<MailList> findAll();
}
