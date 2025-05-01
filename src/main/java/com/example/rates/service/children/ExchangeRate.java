package com.example.rates.service.children;

import com.example.rates.service.AbstractHttpRateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpRequest;

import org.json.JSONObject;

@Component
public class ExchangeRate extends AbstractHttpRateProvider {

    private final String URL;
    private final String TOKEN;

    @Autowired
    public ExchangeRate(
            @Value("${EXCHANGE_URL}") String URL,
            @Value("${EXCHANGE_API_KEY}") String TOKEN
    ) {
        this.URL = URL;
        this.TOKEN = TOKEN;
    }

    @Override
    public String getName() {
        return "Exchange Rate";
    }

    @Override
    protected HttpRequest buildRequest(String from, String to) {
        String uri = String.format(this.URL, this.TOKEN, from, to);
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
    }

    @Override
    protected BigDecimal parseRate(String rawResponse) {
        // e.g. { "conversion_rate": 0.7345, … }
        JSONObject o = new JSONObject(rawResponse);
        return o.getBigDecimal("conversion_rate");
    }
}
