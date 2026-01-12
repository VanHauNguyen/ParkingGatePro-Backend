package com.parking.parkingsystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_events")
public class ParkingEvent {

    public enum EventType { IN, OUT }
    public enum Status { OK, BLOCKED, MANUAL_REVIEW }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @Column(name = "event_time", nullable = false)
    private LocalDateTime eventTime;

    @Column(name = "gate_id", nullable = false)
    private Long gateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle; // bạn đã có entity Vehicle

    @Column(name = "plate_no_raw")
    private String plateNoRaw;

    @Column(name = "plate_no_norm")
    private String plateNoNorm;

    @Column(name = "ocr_confidence")
    private BigDecimal ocrConfidence;

    @Column(name = "snapshot_path", columnDefinition = "TEXT")
    private String snapshotPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.OK;

    @Column(name = "handled_by")
    private Long handledBy; // FK tới users.id nhưng hiện tại ta giữ Long cho đơn giản

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    // ===== getters/setters =====

    public Long getId() { return id; }

    public EventType getEventType() { return eventType; }
    public void setEventType(EventType eventType) { this.eventType = eventType; }

    public LocalDateTime getEventTime() { return eventTime; }
    public void setEventTime(LocalDateTime eventTime) { this.eventTime = eventTime; }

    public Long getGateId() { return gateId; }
    public void setGateId(Long gateId) { this.gateId = gateId; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public String getPlateNoRaw() { return plateNoRaw; }
    public void setPlateNoRaw(String plateNoRaw) { this.plateNoRaw = plateNoRaw; }

    public String getPlateNoNorm() { return plateNoNorm; }
    public void setPlateNoNorm(String plateNoNorm) { this.plateNoNorm = plateNoNorm; }

    public BigDecimal getOcrConfidence() { return ocrConfidence; }
    public void setOcrConfidence(BigDecimal ocrConfidence) { this.ocrConfidence = ocrConfidence; }

    public String getSnapshotPath() { return snapshotPath; }
    public void setSnapshotPath(String snapshotPath) { this.snapshotPath = snapshotPath; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Long getHandledBy() { return handledBy; }
    public void setHandledBy(Long handledBy) { this.handledBy = handledBy; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
