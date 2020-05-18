package ru.spb.avetall.hw14integrationapp.modules.question.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spb.avetall.hw14integrationapp.modules.question.domain.Token;

public interface TokenDao extends JpaRepository<Token, Long> {
    Token findByTokenName(String tokenName);
}
