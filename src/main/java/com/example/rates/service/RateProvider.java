package com.example.rates.service;


import java.math.BigDecimal;

public interface RateProvider {
        String getName();
        BigDecimal getRate(String from, String to);

}
