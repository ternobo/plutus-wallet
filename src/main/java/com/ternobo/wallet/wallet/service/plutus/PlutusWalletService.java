package com.ternobo.wallet.wallet.service.plutus;

import com.ternobo.wallet.transaction.records.Transaction;
import com.ternobo.wallet.wallet.records.Wallet;
import com.ternobo.wallet.wallet.service.WalletService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PlutusWalletService extends WalletService {
    @Transactional
    Transaction transfer(double amount, UUID sourceWalletId, UUID targetWalletId);

    Transaction transfer(double amount, UUID sourceWalletId, UUID targetWalletId, String description, Map<String, Object> meta);

    List<Wallet> getUserWallets(String username);
}
