package com.ternobo.wallet.transaction.repositories;

import com.ternobo.wallet.transaction.records.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
    Page<Transaction> findByWalletIdAndWalletUserUsername(Long walletId, String username, Pageable pageable);

    Page<Transaction> findByWalletUserUsername(String username, Pageable pageable);

    long deleteByTransactionId(String transactionId);

    Optional<Transaction> findByTransactionId(String transactionId);


}