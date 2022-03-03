package com.ternobo.wallet.currency;

import com.ternobo.wallet.currency.exceptions.CurrencyNotFoundException;
import com.ternobo.wallet.currency.records.CurrencyPrice;
import com.ternobo.wallet.currency.repositories.CurrencyPriceRepository;
import com.ternobo.wallet.wallet.records.Currency;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class CurrencyPriceService {

    private final CurrencyPriceRepository repository;

    @Autowired
    public CurrencyPriceService(CurrencyPriceRepository repository) {
        this.repository = repository;
    }

    @Scheduled(cron = "0 0 * * *")
    public double getUSDRealTime() {
        CurrencyPrice usd = this.repository.findByCurrency(Currency.USD).orElseGet(() -> new CurrencyPrice(Currency.USD, 0));
        try {
            Document document = Jsoup.connect("https://www.tgju.org/").get();
            String usdPrice = Objects.requireNonNull(document.body().selectFirst("span[data-market-p=\"sana_buy_usd\"]")).html().replace(",", "");
            double price = Double.parseDouble(usdPrice);
            usd.setPrice(price);
            this.repository.save(usd);
            return price;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return usd.getPrice();
    }

    public double exchangeIRRToPlutus(int amount) {
        double usdPrice = this.repository.findByCurrency(Currency.USD).orElseThrow(CurrencyNotFoundException::new).getPrice();
        return Math.round((amount / usdPrice) * (100 / 1.4));
    }

    public double exchangePlutusToIRR(int amount) {
        double usdPrice = this.repository.findByCurrency(Currency.USD).orElseThrow(CurrencyNotFoundException::new).getPrice();
        return Math.round((usdPrice * 0.014) * amount);
    }

}
