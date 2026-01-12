package com.parking.parkingsystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_sessions")
public class ParkingSession {

    public enum FeeStatus { UNPAID, PAID, FREE }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // vehicle_id (FK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    // checkin_event_id
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "checkin_event_id", nullable = false, unique = true)
    private ParkingEvent checkinEvent;

    // checkout_event_id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checkout_event_id", unique = true)
    private ParkingEvent checkoutEvent;

    @Column(name = "checkin_time", nullable = false)
    private LocalDateTime checkinTime;

    @Column(name = "checkout_time")
    private LocalDateTime checkoutTime;

    @Column(name = "fee_amount", nullable = false)
    private BigDecimal feeAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_status", nullable = false)
    private FeeStatus feeStatus = FeeStatus.UNPAID;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    // ===== getters/setters =====

    public Long getId() { return id; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public ParkingEvent getCheckinEvent() { return checkinEvent; }
    public void setCheckinEvent(ParkingEvent checkinEvent) { this.checkinEvent = checkinEvent; }

    public ParkingEvent getCheckoutEvent() { return checkoutEvent; }
    public void setCheckoutEvent(ParkingEvent checkoutEvent) { this.checkoutEvent = checkoutEvent; }

    public LocalDateTime getCheckinTime() { return checkinTime; }
    public void setCheckinTime(LocalDateTime checkinTime) { this.checkinTime = checkinTime; }

    public LocalDateTime getCheckoutTime() { return checkoutTime; }
    public void setCheckoutTime(LocalDateTime checkoutTime) { this.checkoutTime = checkoutTime; }

    public BigDecimal getFeeAmount() { return feeAmount; }
    public void setFeeAmount(BigDecimal feeAmount) { this.feeAmount = feeAmount; }

    public FeeStatus getFeeStatus() { return feeStatus; }
    public void setFeeStatus(FeeStatus feeStatus) { this.feeStatus = feeStatus; }

    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
}
