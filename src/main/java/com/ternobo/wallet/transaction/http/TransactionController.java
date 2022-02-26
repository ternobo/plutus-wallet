package com.ternobo.wallet.transaction.http;

import com.ternobo.wallet.transaction.records.Transaction;
import com.ternobo.wallet.transaction.services.plutus.PlutusTransactionService;
import com.ternobo.wallet.user.records.User;
import com.ternobo.wallet.utils.http.RestfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final PlutusTransactionService transactionService;

    @Autowired
    public TransactionController(PlutusTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public RestfulResponse<Page<Transaction>> index(@RequestParam(defaultValue = "1") @Min(1) @Valid Integer page,
                                                    @RequestParam(defaultValue = "20") @Min(1) Integer size,
                                                    @RequestParam(defaultValue = "DESC") String direction) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        System.out.println(user.getUsername());
        return new RestfulResponse<>(true, this.transactionService.getTransactionsByUser(user.getUsername(),
                PageRequest.of(
                        page - 1,
                        size,
                        Sort.by(Sort.Direction.fromString(direction), "createdAt")
                )
        )
        );
    }
}
