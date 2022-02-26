package com.ternobo.wallet.transaction.services.plutus;

import com.ternobo.wallet.transaction.records.Transaction;
import com.ternobo.wallet.transaction.records.TransactionEvent;
import com.ternobo.wallet.transaction.repositories.TransactionRepository;
import com.ternobo.wallet.transaction.services.TransactionService;
import com.ternobo.wallet.wallet.service.plutus.PlutusWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlutusTransactionService implements TransactionService {

    private final TransactionRepository repository;
    private final PlutusWalletService walletService;

    @Autowired
    public PlutusTransactionService(TransactionRepository repository, PlutusWalletService walletService) {
        this.repository = repository;
        this.walletService = walletService;
    }

    @Override
    public void rejectTransaction(String transactionId) {
        Transaction transaction = this.repository.findByTransactionId(transactionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
        this.repository.delete(transaction);
        this.walletService.recalculateBalance(transaction.getWallet().getId());
    }

    @Override
    public Page<Transaction> getTransactions(Long walletId, String username, Pageable pageable) {
        return this.repository.findByWalletIdAndWalletUserUsername(walletId, username, pageable);
    }

    @Override
    public Page<Transaction> getTransactionsByEvent(Long walletId, Long userId, TransactionEvent event) {
        return null;
    }

    @Override
    public Page<Transaction> getTransactionsByUser(String username, Pageable pageable) {
        System.out.println(this.repository.findByWalletUserUsername(username, pageable).getTotalElements());
        return this.repository.findByWalletUserUsername(username, pageable);
    }

    @Override
    public Page<Transaction> getTransactionsByUserAndEvent(Long userId, TransactionEvent event) {
        return null;
    }
}
