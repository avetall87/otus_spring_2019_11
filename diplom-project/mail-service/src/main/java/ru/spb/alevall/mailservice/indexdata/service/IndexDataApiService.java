package ru.spb.alevall.mailservice.indexdata.service;


import ru.spb.alevall.common.domain.link.QuestionLink;

import java.util.List;

public interface IndexDataApiService {
    List<QuestionLink> receiveData();
}
