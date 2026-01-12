package com.parking.parkingsystem.dto;

import java.time.LocalDateTime;

public class ParkingInResponse {

    private Long eventId;
    private Long sessionId;

    private String plateNoRaw;
    private String plateNoNorm;

    private boolean monthlyFree;      // có vé tháng hay không (FREE)
    private String feeStatus;         // UNPAID / FREE

    private LocalDateTime checkinTime;

    public ParkingInResponse() {}

    public ParkingInResponse(Long eventId, Long sessionId,
                             String plateNoRaw, String plateNoNorm,
                             boolean monthlyFree, String feeStatus,
                             LocalDateTime checkinTime) {
        this.eventId = eventId;
        this.sessionId = sessionId;
        this.plateNoRaw = plateNoRaw;
        this.plateNoNorm = plateNoNorm;
        this.monthlyFree = monthlyFree;
        this.feeStatus = feeStatus;
        this.checkinTime = checkinTime;
    }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }

    public String getPlateNoRaw() { return plateNoRaw; }
    public void setPlateNoRaw(String plateNoRaw) { this.plateNoRaw = plateNoRaw; }

    public String getPlateNoNorm() { return plateNoNorm; }
    public void setPlateNoNorm(String plateNoNorm) { this.plateNoNorm = plateNoNorm; }

    public boolean isMonthlyFree() { return monthlyFree; }
    public void setMonthlyFree(boolean monthlyFree) { this.monthlyFree = monthlyFree; }

    public String getFeeStatus() { return feeStatus; }
    public void setFeeStatus(String feeStatus) { this.feeStatus = feeStatus; }

    public LocalDateTime getCheckinTime() { return checkinTime; }
    public void setCheckinTime(LocalDateTime checkinTime) { this.checkinTime = checkinTime; }
}
