package com.example.rates.service;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractHttpRateProvider implements RateProvider {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Logger log = LoggerFactory.getLogger(AbstractHttpRateProvider.class);

    @Override
    public BigDecimal getRate(String from, String to) {
        try {
            // buildRequest is implemented by each subclass
            HttpRequest request = buildRequest(from, to);
            // 2) log the outgoing request
            log.info("→ [{}] {} {}", getName(), request.method(), request.uri());
            request.headers().map().forEach((k, v) -> log.debug("  header: {}={}", k, v));

            // 3) send and receive
            HttpResponse<String> resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // 4) log the response
            log.info("← [{}] status={} body={}", getName(), resp.statusCode(), resp.body());

            // parseRate is implemented by each subclass
            return parseRate(resp.body());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch rate from " + getName(), e);
        }
    }

    /** child builds the full GET/POST request (URI, headers, body) */
    protected abstract HttpRequest buildRequest(String from, String to);

    /** child parses the raw response JSON/text into a BigDecimal */
    protected abstract BigDecimal parseRate(String rawResponse);

    @Override
    public abstract String getName();
}
