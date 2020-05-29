package ru.spb.avetall.indexerservice.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.spb.avetall.indexerservice.domain.TokenIndex;

public interface IndexTokenDataDao extends ElasticsearchRepository<TokenIndex, String> {

}
