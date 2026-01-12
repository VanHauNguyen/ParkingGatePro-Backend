package com.parking.parkingsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Raw (có thể có dấu -)
    @Column(name = "plate_no", nullable = false, unique = true, length = 20)
    private String plateNo;

    // Normalized (bỏ dấu, uppercase) -> dùng cho lookup
    @Column(name = "plate_no_norm", nullable = false, unique = true, length = 20)
    private String plateNoNorm;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType = VehicleType.CAR;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_phone")
    private String ownerPhone;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (vehicleType == null) vehicleType = VehicleType.CAR;

        // nếu chưa set norm thì set theo raw (fallback)
        if (plateNoNorm == null || plateNoNorm.isBlank()) {
            plateNoNorm = normalizePlate(plateNo);
        }
    }

    private String normalizePlate(String s) {
        if (s == null) return null;
        return s.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
    }

    // ===== getters/setters =====
    public Long getId() { return id; }

    public String getPlateNo() { return plateNo; }
    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
        // giữ cho plateNoNorm luôn sync (nếu muốn)
        this.plateNoNorm = normalizePlate(plateNo);
    }

    public String getPlateNoNorm() { return plateNoNorm; }
    public void setPlateNoNorm(String plateNoNorm) { this.plateNoNorm = plateNoNorm; }

    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getOwnerPhone() { return ownerPhone; }
    public void setOwnerPhone(String ownerPhone) { this.ownerPhone = ownerPhone; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
