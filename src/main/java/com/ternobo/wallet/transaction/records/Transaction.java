package com.ternobo.wallet.transaction.records;

import com.ternobo.wallet.tools.HashMapConverter;
import com.ternobo.wallet.user.records.User;
import com.ternobo.wallet.wallet.records.Wallet;
import lombok.*;
import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private TransactionEvent event;

    @Column
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @Column
    private String transactionId;

    @Column
    private String description;

    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> meta;
}