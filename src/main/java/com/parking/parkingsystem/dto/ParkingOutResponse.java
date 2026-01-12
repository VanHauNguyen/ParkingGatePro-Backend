package com.parking.parkingsystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingOutResponse {

    private Long eventId;
    private Long sessionId;

    private String plateNoRaw;
    private String plateNoNorm;

    private LocalDateTime checkinTime;
    private LocalDateTime checkoutTime;

    private String feeStatus;     // FREE / UNPAID / PAID
    private BigDecimal feeAmount; // hiện tại có thể 0, phase sau tính tiền

    public ParkingOutResponse() {}

    public ParkingOutResponse(Long eventId, Long sessionId,
                              String plateNoRaw, String plateNoNorm,
                              LocalDateTime checkinTime, LocalDateTime checkoutTime,
                              String feeStatus, BigDecimal feeAmount) {
        this.eventId = eventId;
        this.sessionId = sessionId;
        this.plateNoRaw = plateNoRaw;
        this.plateNoNorm = plateNoNorm;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.feeStatus = feeStatus;
        this.feeAmount = feeAmount;
    }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }

    public String getPlateNoRaw() { return plateNoRaw; }
    public void setPlateNoRaw(String plateNoRaw) { this.plateNoRaw = plateNoRaw; }

    public String getPlateNoNorm() { return plateNoNorm; }
    public void setPlateNoNorm(String plateNoNorm) { this.plateNoNorm = plateNoNorm; }

    public LocalDateTime getCheckinTime() { return checkinTime; }
    public void setCheckinTime(LocalDateTime checkinTime) { this.checkinTime = checkinTime; }

    public LocalDateTime getCheckoutTime() { return checkoutTime; }
    public void setCheckoutTime(LocalDateTime checkoutTime) { this.checkoutTime = checkoutTime; }

    public String getFeeStatus() { return feeStatus; }
    public void setFeeStatus(String feeStatus) { this.feeStatus = feeStatus; }

    public BigDecimal getFeeAmount() { return feeAmount; }
    public void setFeeAmount(BigDecimal feeAmount) { this.feeAmount = feeAmount; }
}
