package com.example.rates.service.children;

import com.example.rates.service.AbstractHttpRateProvider;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpRequest;

@Component
public class WiseRate extends AbstractHttpRateProvider {

    private final String URL;
    private final String TOKEN;

    @Autowired
    public WiseRate(
            @Value("${WISE_URL}") String URL,
            @Value("${WISE_API_KEY}") String TOKEN
    ) {
        this.URL = URL;
        this.TOKEN = TOKEN;
    }
    @Override
    public String getName() {
        return "WISE";
    }

    @Override
    protected HttpRequest buildRequest(String from, String to) {
        String uri = String.format(this.URL, from, to);
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Authorization", "Bearer " + this.TOKEN)
                .GET()
                .build();
    }

    @Override
    protected BigDecimal parseRate(String rawResponse) {
        // e.g. [ { "rate": 1.2345, … }, … ]
        JSONArray arr = new JSONArray(rawResponse);
        return arr.getJSONObject(0).getBigDecimal("rate");
    }
}
