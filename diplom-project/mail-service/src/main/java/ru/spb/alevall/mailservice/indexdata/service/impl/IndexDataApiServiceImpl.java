package ru.spb.alevall.mailservice.indexdata.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.spb.alevall.common.domain.link.QuestionLink;
import ru.spb.alevall.mailservice.indexdata.service.IndexDataApiService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class IndexDataApiServiceImpl implements IndexDataApiService {

    private static final String INDEX_SERVICE_API_PATH = "/api/v1/all/question";

    @Value("${index.service.url}")
    private String indexServiceUrl;

    private final RestTemplate restTemplate;

    @Override
    public List<QuestionLink> receiveData() {
        ResponseEntity<QuestionLink[]> responseEntity = restTemplate.getForEntity(indexServiceUrl + INDEX_SERVICE_API_PATH, QuestionLink[].class);

        if (nonNull(responseEntity.getBody())) {
            return Arrays.asList(responseEntity.getBody());
        }

        return Collections.emptyList();
    }
}
