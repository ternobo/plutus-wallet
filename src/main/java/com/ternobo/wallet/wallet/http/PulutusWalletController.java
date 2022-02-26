package com.ternobo.wallet.wallet.http;

import com.ternobo.wallet.transaction.records.Transaction;
import com.ternobo.wallet.utils.http.RestfulResponse;
import com.ternobo.wallet.wallet.http.requests.TransferRequest;
import com.ternobo.wallet.wallet.records.Wallet;
import com.ternobo.wallet.wallet.service.plutus.PlutusWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/wallet")
public class PulutusWalletController {

    private final PlutusWalletService walletService;

    @Autowired
    public PulutusWalletController(PlutusWalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    @ResponseBody
    public RestfulResponse<List<Wallet>> index(Authentication auth) {
        String username = auth.getName();
        return new RestfulResponse<>(true, this.walletService.getUserWallets(username));
    }

    @PostMapping("transfer")
    @ResponseBody
    public RestfulResponse<Transaction> transfer(@RequestBody @Valid TransferRequest transfer) {
        Transaction transferTransaction = this.walletService.transfer(transfer.getAmount(), transfer.getSourceWalletToken(), transfer.getTargetWalletToken(), transfer.getDescription(), transfer.getMeta());
        return new RestfulResponse<>(transferTransaction != null, transferTransaction);
    }
}
