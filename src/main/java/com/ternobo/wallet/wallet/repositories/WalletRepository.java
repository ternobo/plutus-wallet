package com.ternobo.wallet.wallet.repositories;

import com.ternobo.wallet.wallet.records.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByToken(@Param("uuid") UUID token);

    boolean deleteByToken(UUID token);

    @Query("select w from Wallet w where w.user.username = ?1")
    List<Wallet> findByUserUsername(String username);
}