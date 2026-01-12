package com.parking.parkingsystem.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "monthly_subscriptions")
public class MonthlySubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // bạn đang dùng vehicleId (Long) chứ chưa map quan hệ Vehicle
    @Column(name = "vehicle_id", nullable = false)
    private Long vehicleId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "plan_name", nullable = false)
    private String planName = "Monthly";

    @Column(name = "price", nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    /**
     * DB column: ENUM('ACTIVE','EXPIRED','SUSPENDED')
     * - @Enumerated(EnumType.STRING) => store "ACTIVE"...
     * - @JdbcTypeCode(SqlTypes.VARCHAR) => Hibernate validate expecting VARCHAR
     *   Nhưng DB là ENUM (Types#CHAR) nên mismatch.
     *
     * Fix: dùng SqlTypes.ENUM để Hibernate hiểu đây là ENUM.
     */
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ENUM)
    @Column(name = "status", nullable = false)
    private MonthlyStatus status = MonthlyStatus.ACTIVE;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (status == null) status = MonthlyStatus.ACTIVE;
        if (planName == null) planName = "Monthly";
        if (price == null) price = BigDecimal.ZERO;
    }

    // ===== getters/setters =====
    public Long getId() { return id; }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public MonthlyStatus getStatus() { return status; }
    public void setStatus(MonthlyStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
