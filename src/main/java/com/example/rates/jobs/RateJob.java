package com.example.rates.jobs;

import com.example.rates.service.RateService;
import org.springframework.scheduling.annotation.Scheduled;

// * This is where add all the CronJobsz

public class RateJob {
    private final RateService rateService;

    public RateJob(RateService rateService){
        this.rateService = rateService;
    }

    @Scheduled(cron = "0 */5 * * * *", zone = "Asia/Singapore") // TODO , change this and update use env-valkey database for configuration custom
    public void sendMailAutomation() {
        rateService.sendMail("exelbert2010@gmail.com","SGD","PHP");
    }

}
