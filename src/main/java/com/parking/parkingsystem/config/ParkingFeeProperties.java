package com.parking.parkingsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "parking.fee")
public class ParkingFeeProperties {

    private int freeMinutes = 30;
    private BigDecimal ratePerHour = BigDecimal.valueOf(30);
    private BigDecimal dailyCap = BigDecimal.valueOf(300);

    public int getFreeMinutes() { return freeMinutes; }
    public void setFreeMinutes(int freeMinutes) { this.freeMinutes = freeMinutes; }

    public BigDecimal getRatePerHour() { return ratePerHour; }
    public void setRatePerHour(BigDecimal ratePerHour) { this.ratePerHour = ratePerHour; }

    public BigDecimal getDailyCap() { return dailyCap; }
    public void setDailyCap(BigDecimal dailyCap) { this.dailyCap = dailyCap; }
}
