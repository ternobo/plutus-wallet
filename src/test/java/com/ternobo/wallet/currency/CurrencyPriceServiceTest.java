package com.ternobo.wallet.currency;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ActiveProfiles("test")
class CurrencyPriceServiceTest {

    @Autowired
    private CurrencyPriceService service;

    @Test
    void getUSDRealTime() {
        int usdRealTime = this.service.getUSDRealTime();
        assertNotEquals(0, usdRealTime);
    }
}