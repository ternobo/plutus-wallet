package com.ternobo.wallet.transaction.records;

import com.ternobo.wallet.tools.HashMapConverter;
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
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private TransactionEvent event;

    @Column
    private double amount;

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