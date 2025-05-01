package com.example.rates.service;

import com.example.rates.dto.RateQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateService {
    private final List<RateProvider> providers;

    @Autowired
    public RateService(List<RateProvider> providers) {
        this.providers = providers;
    }

    public List<RateQuote> getAllRates(String from, String to) {
        return providers.stream()
                .map(p -> new RateQuote(p.getName(), p.getRate(from, to)))
                .collect(Collectors.toList());
    }
}