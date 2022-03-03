package com.ternobo.wallet;

import com.github.javafaker.Faker;
import com.ternobo.wallet.transaction.records.TransactionEvent;
import com.ternobo.wallet.user.http.requests.UserDTO;
import com.ternobo.wallet.user.records.User;
import com.ternobo.wallet.user.service.UserService;
import com.ternobo.wallet.wallet.records.Wallet;
import com.ternobo.wallet.wallet.service.plutus.PlutusWalletService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserWalletTest {
    protected static Faker faker;
    protected final double initialBalance = 300;
    @Autowired
    protected PlutusWalletService walletService;

    @Autowired
    protected UserService userService;
    // Users
    protected User firstUser;
    protected User secondUser;
    protected Wallet firstWallet;
    protected Wallet secondWallet;

    @BeforeAll
    static void beforeAll() {
        faker = new Faker();
    }

    @BeforeEach
    void setUp() {
        this.firstUser = this.userService.createUser(new UserDTO(
                faker.name().fullName(), faker.name().username(), "123456", "123456"
        ));
        this.secondUser = this.userService.createUser(new UserDTO(
                faker.name().fullName(), faker.name().username(), "123456", "123456"
        ));

        this.firstWallet = this.firstUser.getWallets().get(0);
        this.secondWallet = this.secondUser.getWallets().get(0);

        this.firstWallet = this.walletService.createTransaction(this.firstWallet.getId(), this.initialBalance, TransactionEvent.CHARGE);
        this.secondWallet = this.walletService.createTransaction(this.secondWallet.getId(), this.initialBalance, TransactionEvent.CHARGE);
    }
}
