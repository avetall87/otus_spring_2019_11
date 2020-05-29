package ru.spb.alevall.mailservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.spb.alevall.mailservice.domain.MailListLog;

import java.util.List;

public interface MailListLogDao extends JpaRepository<MailListLog, Long> {

    @Query(value = "select * from mail_lists_logs where date_trunc('day',transfer_date) = date_trunc('day',current_timestamp) " +
                   " and mail_list_id = :mailListId", nativeQuery = true)
    List<MailListLog> findAllForCurrentDay(@Param("mailListId") Long mailListId);
}
