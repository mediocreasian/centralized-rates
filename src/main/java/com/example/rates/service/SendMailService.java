package com.example.rates.service;

import com.example.rates.dto.RateQuote;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendMailService {
    @Autowired
    private JavaMailSender mailSender;

    private static final Logger log = LoggerFactory.getLogger(SendMailService.class);

    public void sendMail(String email, List<RateQuote> allRates) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email); // 📨 dynamic email address
            helper.setSubject("Available Rates");
            helper.setText("Rates available: " + allRates);
            mailSender.send(message);
            log.info("→  {} {}", "Sending Mail", "to " + email);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
