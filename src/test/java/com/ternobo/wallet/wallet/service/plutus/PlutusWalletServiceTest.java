package com.ternobo.wallet.wallet.service.plutus;

import com.github.javafaker.Faker;
import com.ternobo.wallet.transaction.records.TransactionEvent;
import com.ternobo.wallet.user.http.requests.UserDTO;
import com.ternobo.wallet.user.records.User;
import com.ternobo.wallet.user.service.UserService;
import com.ternobo.wallet.wallet.records.Wallet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class PlutusWalletServiceTest {

    private static Faker faker;
    private final double initialBalance = 300;
    @Autowired
    private PlutusWalletService walletService;

    @Autowired
    private UserService userService;
    // Users
    private User firstUser;
    private User secondUser;
    private Wallet firstWallet;
    private Wallet secondWallet;

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


    @Test
    void getCacheBalance() {
        assertEquals("Wallet Cache Balance is not sync", this.initialBalance, this.firstWallet.getCacheBalance());
        assertEquals("Wallet2 Cache Balance is not sync", this.initialBalance, this.secondWallet.getCacheBalance());
    }

    @Test
    void recalculateBalance() {
        double balance1 = this.walletService.recalculateBalance(this.firstWallet.getId());
        double balance2 = this.walletService.recalculateBalance(this.secondWallet.getId());

        assertEquals("Wallet recalculateBalance Balance is not sync", balance1, this.firstWallet.getCacheBalance());
        assertEquals("Wallet2 recalculateBalance Balance is not sync", balance2, this.secondWallet.getCacheBalance());

        assertEquals("Wallet Cache Balance is not sync", this.initialBalance, this.firstWallet.getCacheBalance());
        assertEquals("Wallet2 Cache Balance is not sync", this.initialBalance, this.secondWallet.getCacheBalance());
    }

    @Test
    void createTransaction() {
        this.firstWallet = this.walletService.createTransaction(this.firstWallet.getId(), 10, TransactionEvent.CHARGE);
        this.secondWallet = this.walletService.createTransaction(this.secondUser.getId(), 35, TransactionEvent.CHARGE);

        assertEquals("Add transaction is not working", this.initialBalance + 10, this.firstWallet.getCacheBalance());
        assertEquals("Add transaction is not working", this.initialBalance + 35, this.secondWallet.getCacheBalance());
    }

    @Test
    void transfer() {
        this.walletService.transfer(100, this.firstWallet.getId(), this.secondWallet.getId());

        this.firstWallet = this.walletService.findWalletById(this.firstWallet.getId());
        this.secondWallet = this.walletService.findWalletById(this.secondWallet.getId());

        assertEquals(
                "Transfer from first_wallet is not OK",
                this.initialBalance - 100,
                this.firstWallet.getCacheBalance()
        );

        assertEquals("Transfer to second_wallet is not OK", this.initialBalance + 100, this.secondWallet.getCacheBalance());

        assertEquals(
                "Liquidity increased after transfer!",
                this.firstWallet.getCacheBalance() + this.secondWallet.getCacheBalance(),
                this.initialBalance + this.initialBalance
        );
    }


}