package com.example.rates.dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mutable bean:
 * - no-args constructor (for frameworks)
 * - all-args constructor for your code
 * - getters, setters, equals, hashCode, toString
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateQuote {
    private String providerName;
    private BigDecimal rate;
}