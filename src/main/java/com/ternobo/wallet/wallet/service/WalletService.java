package com.ternobo.wallet.wallet.service;

import com.ternobo.wallet.transaction.records.TransactionEvent;
import com.ternobo.wallet.wallet.records.Wallet;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface WalletService {
    double getCacheBalance(Long walletId);

    double recalculateBalance(Long walletId);

    Wallet createTransaction(Long walletId, double amount, TransactionEvent event);

    Wallet findWalletByToken(UUID walletToken);
    Wallet findWalletById(Long walletId);

    Wallet createTransaction(Long walletId, double amount, TransactionEvent event, String transactionId);
}
