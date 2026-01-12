package com.parking.parkingsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ParkingInRequest {

    @NotBlank
    private String plateNo;      // raw input, ví dụ "ABC-1234"

    @NotNull
    private Long gateId;         // bắt buộc vì DB gate_id NOT NULL

    private String snapshotPath; // optional

    public String getPlateNo() { return plateNo; }
    public void setPlateNo(String plateNo) { this.plateNo = plateNo; }

    public Long getGateId() { return gateId; }
    public void setGateId(Long gateId) { this.gateId = gateId; }

    public String getSnapshotPath() { return snapshotPath; }
    public void setSnapshotPath(String snapshotPath) { this.snapshotPath = snapshotPath; }
}
