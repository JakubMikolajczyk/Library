package com.Library.restAPI.repository;

import com.Library.restAPI.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findTokenByIdAndExpireDateGreaterThan(UUID id, Date date);
    @Modifying
    @Query("DELETE FROM Token WHERE user.id=:userId")
    void deleteAllByUserId(@Param("userId") Long userId);
}
