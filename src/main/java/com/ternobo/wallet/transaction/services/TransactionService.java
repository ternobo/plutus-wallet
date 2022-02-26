package com.ternobo.wallet.transaction.services;

import com.ternobo.wallet.transaction.records.Transaction;
import com.ternobo.wallet.transaction.records.TransactionEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    void rejectTransaction(String transactionId);
    Page<Transaction> getTransactions(Long walletId, String username, Pageable pageable) ;
    Page<Transaction> getTransactionsByEvent(Long walletId, Long userId, TransactionEvent event);
    Page<Transaction> getTransactionsByUser(String username, Pageable pageable) ;
    Page<Transaction> getTransactionsByUserAndEvent(Long userId, TransactionEvent event);
}
