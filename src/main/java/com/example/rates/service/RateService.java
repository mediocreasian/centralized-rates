package com.example.rates.service;

import com.example.rates.dto.RateQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateService {
    private final List<RateProvider> providers;
    private final SendMailService sendMailService;

    public RateService(List<RateProvider> providers, SendMailService sendMailService) {
        this.providers = providers;
        this.sendMailService = sendMailService;
    }

    public List<RateQuote> getAllRates(String from, String to) {
        return providers.stream()
                .map(p -> new RateQuote(p.getName(), p.getRate(from, to)))
                .collect(Collectors.toList());
    }

    public void sendMail(String email, String from, String to) {
        sendMailService.sendMail(email, getAllRates(from, to));
    }

}