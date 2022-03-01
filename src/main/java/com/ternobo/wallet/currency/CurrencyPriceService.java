package com.ternobo.wallet.currency;

import com.ternobo.wallet.currency.records.CurrencyPrice;
import com.ternobo.wallet.currency.repositories.CurrencyPriceRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
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

    public int getUSDRealTime() {
        CurrencyPrice usd = this.repository.findByCurrency("usd").orElseGet(() -> new CurrencyPrice("usd",0));
        try {
            Document document = Jsoup.connect("https://www.tgju.org/").get();
            String usdPrice = Objects.requireNonNull(document.body().selectFirst("span[data-market-p=\"sana_buy_usd\"]")).html().replace(",", "");
            int price = Integer.parseInt(usdPrice);
            usd.setPrice(price);
            this.repository.save(usd);
            return price;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return usd.getPrice();
    }

}
