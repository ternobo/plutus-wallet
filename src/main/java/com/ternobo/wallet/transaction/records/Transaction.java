package com.ternobo.wallet.transaction.records;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ternobo.wallet.tools.HashMapConverter;
import com.ternobo.wallet.tools.audits.TimestampAudit;
import com.ternobo.wallet.wallet.records.Wallet;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

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

    @JsonProperty("transaction_time")
    public String getTransactionDateTime(){
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(Objects.requireNonNullElseGet(this.createdAt, () -> Date.valueOf(LocalDate.now())));
    }

}