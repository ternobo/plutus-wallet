package com.ternobo.wallet.payment.records;

import com.ternobo.wallet.tools.HashMapConverter;
import com.ternobo.wallet.tools.audits.TimestampAudit;
import com.ternobo.wallet.wallet.records.Currency;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "payments")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends TimestampAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentDriver driver;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentAction action;

    @Column
    private double amount;

    @Column
    private String description;

    @Column(unique = true, name = "transaction_id")
    private String transactionId;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "verify_status")
    private boolean verificationStatus;

    @Column(name = "verify_date")
    private Date verifyDate;

    @Column
    @Enumerated(EnumType.STRING)
    private Currency currency;
    
    @Column
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> meta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Payment payment = (Payment) o;
        return id != null && Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
