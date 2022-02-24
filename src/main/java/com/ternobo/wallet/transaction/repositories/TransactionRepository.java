package com.ternobo.wallet.transaction.repositories;

import com.ternobo.wallet.transaction.records.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
    Page<Transaction> findByWalletIdAndWalletUserId(Long id, Long id1, Pageable pageable);
}