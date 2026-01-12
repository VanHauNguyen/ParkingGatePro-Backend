package com.parking.parkingsystem.dto;

import java.math.BigDecimal;

public class MonthlySubResponse {
    private Long id;
    private Long vehicleId;
    private String startDate;
    private String endDate;
    private String status;
    private String planName;
    private BigDecimal price;

    public MonthlySubResponse(Long id, Long vehicleId, String startDate, String endDate,
                              String status, String planName, BigDecimal price) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.planName = planName;
        this.price = price;
    }

    public Long getId() { return id; }
    public Long getVehicleId() { return vehicleId; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getStatus() { return status; }
    public String getPlanName() { return planName; }
    public BigDecimal getPrice() { return price; }
}
