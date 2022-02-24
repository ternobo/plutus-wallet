package com.ternobo.wallet.transaction.services;

import com.ternobo.wallet.transaction.records.Transaction;
import com.ternobo.wallet.transaction.records.TransactionEvent;
import org.springframework.data.domain.Page;

public interface TransactionService {
    void rejectTransaction(String transactionId);
    Page<Transaction> getTransactions(Long walletId, Long userId);
    Page<Transaction> getTransactionsByEvent(Long walletId, Long userId, TransactionEvent event);
    Page<Transaction> getTransactionsByUser(Long userId);
    Page<Transaction> getTransactionsByUserAndEvent(Long userId, TransactionEvent event);
}
