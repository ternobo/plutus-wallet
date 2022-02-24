package com.ternobo.wallet.transaction.services.plutus;

import com.ternobo.wallet.transaction.records.Transaction;
import com.ternobo.wallet.transaction.records.TransactionEvent;
import com.ternobo.wallet.transaction.repositories.TransactionRepository;
import com.ternobo.wallet.transaction.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

public class PlutusTransactionService implements TransactionService {
    private TransactionRepository repository;

    @Autowired
    public PlutusTransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void rejectTransaction(String transactionId) {

    }

    @Override
    public Page<Transaction> getTransactions(Long walletId, Long userId) {
        return null;
    }

    @Override
    public Page<Transaction> getTransactionsByEvent(Long walletId, Long userId, TransactionEvent event) {
        return null;
    }

    @Override
    public Page<Transaction> getTransactionsByUser(Long userId) {
        return null;
    }

    @Override
    public Page<Transaction> getTransactionsByUserAndEvent(Long userId, TransactionEvent event) {
        return null;
    }
}
