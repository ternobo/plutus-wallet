package com.ternobo.wallet.wallet.service.plutus;

import com.ternobo.wallet.transaction.records.Transaction;
import com.ternobo.wallet.transaction.records.TransactionEvent;
import com.ternobo.wallet.wallet.exceptions.TransactionException;
import com.ternobo.wallet.wallet.exceptions.WalletNotFoundException;
import com.ternobo.wallet.wallet.records.Wallet;
import com.ternobo.wallet.wallet.repositories.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public List<Wallet> getUserWallets(String username) {
        return repository.findByUserUsername(username);
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
                .transactionId(UUID.randomUUID().toString())
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
    public Transaction transfer(double amount, UUID sourceWalletToken, UUID targetWalletToken) {
        return this.transfer(amount, sourceWalletToken, targetWalletToken, null, null);
    }

    @Override
    public Transaction transfer(double amount, UUID sourceWalletToken, UUID targetWalletToken, String description, Map<String, Object> meta) {

        Wallet sourceWallet = this.repository.findByToken(sourceWalletToken).orElseThrow(WalletNotFoundException::new);
        Wallet targetWallet = this.repository.findByToken(targetWalletToken).orElseThrow(WalletNotFoundException::new);

        String transferTransactionId = UUID.randomUUID().toString();

        if(sourceWallet.getCacheBalance() < amount){
            throw new TransactionException("wallet balance is less than transaction amount!");
        }

        Transaction transferTransaction = Transaction.builder()
                .event(TransactionEvent.SEND)
                .amount(-amount)
                .transactionId(transferTransactionId)
                .wallet(sourceWallet)
                .description(description)
                .meta(meta)
                .build();

        // Withdrawal transaction
        sourceWallet.addTransaction(
                transferTransaction
        );
        sourceWallet.setCacheBalance(sourceWallet.getCacheBalance() - amount);
        // Withdrawal transaction end

        // Deposit transaction
        targetWallet.addTransaction(
                Transaction.builder()
                        .event(TransactionEvent.RECEIVE)
                        .amount(amount)
                        .transactionId(transferTransactionId)
                        .wallet(targetWallet)
                        .description(description)
                        .meta(meta)
                        .build()
        );
        targetWallet.setCacheBalance(targetWallet.getCacheBalance() + amount);
        // Deposit transaction end

        this.repository.saveAll(List.of(sourceWallet, targetWallet));
        return transferTransaction;
    }


}
