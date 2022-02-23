package com.ternobo.wallet.wallet.records;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ternobo.wallet.transaction.records.Transaction;
import com.ternobo.wallet.user.records.User;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @Column(name = "cache_balance")
    private double cacheBalance;

    @Column
    private UUID token;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id")
    @Fetch(FetchMode.SELECT)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Transaction> transactions;


    public boolean addTransaction(Transaction transaction) {
        this.transactions = Optional.ofNullable(this.transactions).orElse(new ArrayList<>());
        return this.transactions.add(transaction);
    }
}
