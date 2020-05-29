package ru.spb.alevall.mailservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spb.alevall.mailservice.dao.MailListDao;
import ru.spb.alevall.mailservice.domain.MailList;
import ru.spb.alevall.mailservice.service.MailListService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailListServiceImpl implements MailListService {

    private final MailListDao mailListDao;

    @Override
    @Transactional(readOnly = true)
    public List<MailList> findAll() {
        return mailListDao.findAll();
    }
}
