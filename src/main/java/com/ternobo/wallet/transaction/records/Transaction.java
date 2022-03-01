package com.ternobo.wallet.transaction.records;

import com.ternobo.wallet.tools.HashMapConverter;
import com.ternobo.wallet.tools.audits.TimestampAudit;
import com.ternobo.wallet.wallet.records.Wallet;
import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Map;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Transaction extends TimestampAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
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

    public String getTransactionDateTime(){
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(this.createdAt);
    }

}