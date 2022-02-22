package com.ternobo.wallet.wallet.records;

import com.ternobo.wallet.user.records.User;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@ToString
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
    private User user;

    @Column(name = "cache_balance")
    private Long cacheBalance;

    @Column
    private UUID token;
}
