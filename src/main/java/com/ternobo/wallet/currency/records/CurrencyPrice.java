package com.ternobo.wallet.currency.records;

import com.ternobo.wallet.wallet.records.Currency;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CurrencyPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private double price;

    public CurrencyPrice(Currency currency, int price) {
        this.currency = currency;
        this.price = price;
    }

    public CurrencyPrice(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CurrencyPrice that = (CurrencyPrice) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
