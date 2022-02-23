package com.ternobo.wallet.wallet.service.plutus;

import com.ternobo.wallet.wallet.service.WalletService;
import org.springframework.transaction.annotation.Transactional;

public interface PlutusWalletService extends WalletService {
    @Transactional
    boolean transfer(double amount, Long sourceWalletId, Long targetWalletId);
}
