package ru.spb.alevall.mailservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spb.alevall.mailservice.domain.MailList;

public interface MailListDao extends JpaRepository<MailList, Long> {

}
