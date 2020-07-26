package ru.spb.avetall.indexerservice.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.spb.avetall.indexerservice.domain.LinkIndex;

import java.util.List;

public interface IndexQuestionDataDao extends ElasticsearchRepository<LinkIndex, String> {
    List<LinkIndex> findAll();

    void deleteByCreationDateNotLike(String creationDate);
}
