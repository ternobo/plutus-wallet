package com.ternobo.wallet.auth.repositories;

import com.ternobo.wallet.auth.records.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
    Optional<AccessToken> findByUserId(Object unknownAttr1);
    Optional<AccessToken> findByRefreshToken(String refreshToken);
    boolean existsByRefreshToken(String refreshToken);

}