package ru.spb.avetall.redditservice.modules.question.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.spb.avetall.redditservice.modules.question.domain.Token;

import java.util.List;

public interface TokenDao extends JpaRepository<Token, Long> {
    Token findByTokenName(String tokenName);

    @Modifying
    @Query("update Token t set t.sentToIndex=:sign where t.id = :id")
    void setSentToIndex(@Param("sign") Boolean sign, @Param("id") Long id);

    @Query("select t from Token t where t.sentToIndex is null or t.sentToIndex = false")
    List<Token> findAllNotSendToIndex();
}
