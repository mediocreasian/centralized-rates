package com.example.rates.service.children;

import com.example.rates.service.AbstractHttpRateProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BeaconRate extends AbstractHttpRateProvider {
    @Autowired
    private ObjectMapper objectMapper;

    private final String URL;
    private final String TOKEN;

    public BeaconRate(
            @Value("${BEACON_URL}")    String URL,
            @Value("${BEACON_API_KEY}") String TOKEN
    ) {
        this.URL = URL;
        this.TOKEN      = TOKEN;
    }

    @Override
    public String getName() {
        return "Beacon";
    }

    @Override
    protected HttpRequest buildRequest(String from, String to) {
        String amount = "1"; // * Hardcode static to 1
        String uri = String.format(this.URL, from, to, amount);
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Authorization", "Bearer " + this.TOKEN)
                .GET()
                .build();
    }

    @Override
    protected BigDecimal parseRate(String rawResponse) {
        try {
            JsonNode root = objectMapper.readTree(rawResponse);
            JsonNode valueNode = root.path("response").path("value");
            return valueNode.decimalValue();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Beacon JSON", e);
        }
    }
}
