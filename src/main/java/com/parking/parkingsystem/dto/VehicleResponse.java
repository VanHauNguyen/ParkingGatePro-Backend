package com.parking.parkingsystem.dto;

import java.time.LocalDateTime;

public class VehicleResponse {
    private Long id;
    private String plateNo;
    private LocalDateTime createdAt;

    //  cần để Jackson deserialize (sau này nếu dùng)
    public VehicleResponse() {}

    public VehicleResponse(Long id, String plateNo, LocalDateTime createdAt) {
        this.id = id;
        this.plateNo = plateNo;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlateNo() { return plateNo; }
    public void setPlateNo(String plateNo) { this.plateNo = plateNo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
