package com.parking.parkingsystem.dto;

import java.math.BigDecimal;

public class MonthlySubCreateRequest {
    private Long vehicleId;
    private String startDate; // "YYYY-MM-DD"
    private String endDate;   // "YYYY-MM-DD"
    private String planName;
    private BigDecimal price;

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
