package com.ternobo.wallet.currency.repositories;

import com.ternobo.wallet.currency.records.CurrencyPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyPriceRepository extends JpaRepository<CurrencyPrice, Long> {
    Optional<CurrencyPrice> findByCurrency(String currency);
}