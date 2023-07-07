package com.Library.restAPI.repository;

import com.Library.restAPI.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findTokenById(UUID id);
}
