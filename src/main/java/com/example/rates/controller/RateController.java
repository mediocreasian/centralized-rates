package com.example.rates.controller;

import com.example.rates.dto.RateQuote;
import com.example.rates.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class RateController {

    private final RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    /**
     * GET /api/rates?from=GBP&to=SGD
     *
     * @param from  three-letter currency code (e.g. "GBP")
     * @param to    three-letter currency code (e.g. "SGD")
     * @return a list of RateQuote, one per provider
     */
    @GetMapping
    public List<RateQuote> getAllRates(
            @RequestParam("from") String from,
            @RequestParam("to") String to
    ) {
        return rateService.getAllRates(from, to);
    }
}

