package ru.spb.avetall.hw14integrationapp.modules.question.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.spb.avetall.hw14integrationapp.modules.question.domain.Question;

import java.time.LocalDateTime;
import java.util.List;

public interface QuestionDao extends JpaRepository<Question, Long> {

    @Modifying
    @Query("select q from Question q where q.token IN (select t from Token t where t.tokenName=:token)")
    List<Question> findByTokenName(@Param("token") String token);

    @Query(nativeQuery=true,value ="select count(0) from questions q where q.author_Full_Name=:authorFullName and q.title=:title and q.url=:url and (q.creation_Date=:creationDate or q.creation_Date is null) and q.token_id=:tokenId")
    Long checkQuestionCountByAuthorFullNameAndTitleAndUrlAndCreationDate(@Param("authorFullName") String authorFullName,
                                                                         @Param("title") String title,
                                                                         @Param("url") String url,
                                                                         @Param("creationDate") LocalDateTime creationDate,
                                                                         @Param("tokenId") Long tokenId);
}
