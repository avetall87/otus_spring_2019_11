package ru.spb.avetall.redditservice.modules.question.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.spb.avetall.redditservice.modules.question.domain.Question;
import ru.spb.avetall.redditservice.modules.question.domain.Token;

import java.time.LocalDateTime;
import java.util.List;

public interface QuestionDao extends JpaRepository<Question, Long> {

    @Modifying
    @Query("select q from Question q where q.token IN (select t from Token t where t.tokenName=:token)")
    List<Question> findByTokenName(@Param("token") String token);

    @Query(value ="select count(q) from Question q where q.authorFullName=:authorFullName and q.title=:title and q.url=:url " +
                                   " and (q.creationDate=:creationDate or q.creationDate is null) and q.token=:token")
    Long checkQuestionCountByAuthorFullNameAndTitleAndUrlAndCreationDate(@Param("authorFullName") String authorFullName,
                                                                         @Param("title") String title,
                                                                         @Param("url") String url,
                                                                         @Param("creationDate") LocalDateTime creationDate,
                                                                         @Param("token") Token token);

    @Modifying
    @Query("update Question q set q.sentToIndex=:sign where q.id = :id")
    void setSentToIndex(@Param("sign") Boolean sign, @Param("id") Long id);

    @Query("select q from Question q where q.sentToIndex is null or q.sentToIndex = false")
    List<Question> findAllNotSendToIndex();
}
