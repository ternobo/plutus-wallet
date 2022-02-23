package com.ternobo.wallet.wallet.service.plutus;

import com.ternobo.wallet.transaction.records.Transaction;
import com.ternobo.wallet.transaction.records.TransactionEvent;
import com.ternobo.wallet.wallet.exceptions.WalletNotFoundException;
import com.ternobo.wallet.wallet.records.Wallet;
import com.ternobo.wallet.wallet.repositories.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("plutus.wallet")
public class PlutusWalletServiceJPA implements PlutusWalletService {

    private final WalletRepository repository;

    public PlutusWalletServiceJPA(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public double getCacheBalance(Long walletId) {
        return this.repository.findById(walletId).orElseThrow(WalletNotFoundException::new).getCacheBalance();
    }

    @Override
    @Transactional
    public double recalculateBalance(Long walletId) {
        Wallet wallet = this.repository.findById(walletId).orElseThrow(WalletNotFoundException::new);
        double newBalance = wallet.getTransactions().stream()
                .map(Transaction::getAmount)
                .reduce(0.0d, Double::sum);
        wallet.setCacheBalance(newBalance);
        this.repository.save(wallet);
        return newBalance;
    }

    @Override
    @Transactional
    public Wallet createTransaction(Long walletId, double amount, TransactionEvent event) {
        Wallet wallet = this.repository.findById(walletId).orElseThrow(WalletNotFoundException::new);
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .event(event)
                .wallet(wallet)
                .build();
        wallet.addTransaction(
                transaction
        );
        wallet.setCacheBalance(wallet.getCacheBalance() + amount);
        return this.repository.save(wallet);
    }

    @Override
    public Wallet findWalletByToken(UUID walletToken) {
        return this.repository.findByToken(walletToken).orElseThrow(WalletNotFoundException::new);
    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return this.repository.findById(walletId).orElseThrow(WalletNotFoundException::new);
    }

    @Override
    @Transactional
    public Wallet createTransaction(Long walletId, double amount, TransactionEvent event, String transactionId) {
        Wallet wallet = this.repository.findById(walletId).orElseThrow(WalletNotFoundException::new);
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .event(event)
                .transactionId(transactionId)
                .wallet(wallet)
                .build();
        wallet.addTransaction(
                transaction
        );
        wallet.setCacheBalance(wallet.getCacheBalance() + amount);
        return this.repository.save(wallet);
    }

    @Transactional
    @Override
    public boolean transfer(double amount, Long sourceWalletId, Long targetWalletId) {
        try {
            Wallet sourceWallet = this.repository.findById(sourceWalletId).orElseThrow(WalletNotFoundException::new);
            Wallet targetWallet = this.repository.findById(targetWalletId).orElseThrow(WalletNotFoundException::new);

            String transferTransactionId = UUID.randomUUID().toString();

            // Withdrawal transaction
            sourceWallet.addTransaction(
                    Transaction.builder()
                            .event(TransactionEvent.SEND)
                            .amount(-amount)
                            .transactionId(transferTransactionId)
                            .wallet(sourceWallet)
                            .build()
            );
            System.out.println(sourceWallet.getCacheBalance() - amount);
            sourceWallet.setCacheBalance(sourceWallet.getCacheBalance() - amount);
            // Withdrawal transaction end

            // Deposit transaction
            targetWallet.addTransaction(
                    Transaction.builder()
                            .event(TransactionEvent.SEND)
                            .amount(amount)
                            .transactionId(transferTransactionId)
                            .wallet(targetWallet)
                            .build()
            );
            targetWallet.setCacheBalance(targetWallet.getCacheBalance() + amount);
            // Deposit transaction end

            this.repository.saveAll(List.of(sourceWallet, targetWallet));
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }
}
