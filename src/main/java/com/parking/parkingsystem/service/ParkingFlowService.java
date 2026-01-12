package com.parking.parkingsystem.service;

import com.parking.parkingsystem.entity.MonthlyStatus;
import com.parking.parkingsystem.entity.ParkingEvent;
import com.parking.parkingsystem.entity.ParkingSession;
import com.parking.parkingsystem.entity.Vehicle;
import com.parking.parkingsystem.repository.MonthlySubscriptionRepository;
import com.parking.parkingsystem.repository.ParkingEventRepository;
import com.parking.parkingsystem.repository.ParkingSessionRepository;
import com.parking.parkingsystem.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParkingFlowService {

    private final FeeCalculatorService feeCalculator;
    private final ParkingEventRepository eventRepo;
    private final ParkingSessionRepository sessionRepo;
    private final VehicleRepository vehicleRepo;
    private final MonthlySubscriptionRepository monthlyRepo;

    public ParkingFlowService(
            VehicleRepository vehicleRepo,
            ParkingEventRepository eventRepo,
            ParkingSessionRepository sessionRepo,
            MonthlySubscriptionRepository monthlyRepo,
            FeeCalculatorService feeCalculator
    ) {
        this.vehicleRepo = vehicleRepo;
        this.eventRepo = eventRepo;
        this.sessionRepo = sessionRepo;
        this.monthlyRepo = monthlyRepo;
        this.feeCalculator = feeCalculator;
    }

    // ========================
    // Utils
    // ========================
    public String normalizePlate(String raw) {
        if (raw == null) return "";
        return raw.trim()
                .replaceAll("[^A-Za-z0-9]", "")
                .toUpperCase();
    }

    private String requireNormalizedPlate(String plateRaw) {
        String norm = normalizePlate(plateRaw);
        if (norm.isBlank()) throw new IllegalArgumentException("Invalid plateNo");
        return norm;
    }

    public boolean hasActiveMonthly(Long vehicleId) {
        return monthlyRepo.hasActiveToday(vehicleId, LocalDate.now(), MonthlyStatus.ACTIVE);
    }

    // ========================
    // Event
    // ========================
    @Transactional
    public ParkingEvent createEvent(
            ParkingEvent.EventType type,
            Long gateId,
            String plateRaw,
            String snapshotPath
    ) {
        String norm = requireNormalizedPlate(plateRaw);

        Optional<Vehicle> vehicleOpt = vehicleRepo.findByPlateNo(norm);

        ParkingEvent e = new ParkingEvent();
        e.setEventType(type);
        e.setEventTime(LocalDateTime.now());
        e.setGateId(gateId);
        e.setPlateNoRaw(plateRaw);
        e.setPlateNoNorm(norm);
        e.setSnapshotPath(snapshotPath);
        e.setStatus(ParkingEvent.Status.OK);

        vehicleOpt.ifPresent(e::setVehicle);

        return eventRepo.save(e);
    }

    // ========================
    // Session
    // ========================
    @Transactional
    public ParkingSession openSession(Vehicle vehicle, ParkingEvent checkinEvent) {
        ParkingSession s = new ParkingSession();
        s.setVehicle(vehicle);
        s.setCheckinEvent(checkinEvent);
        s.setCheckinTime(checkinEvent.getEventTime());

        if (hasActiveMonthly(vehicle.getId())) {
            s.setFeeStatus(ParkingSession.FeeStatus.FREE);
            s.setFeeAmount(BigDecimal.ZERO);
        } else {
            s.setFeeStatus(ParkingSession.FeeStatus.UNPAID);
            s.setFeeAmount(BigDecimal.ZERO);
        }

        return sessionRepo.save(s);
    }

    @Transactional
    public ParkingSession closeSession(ParkingSession session, ParkingEvent checkoutEvent) {
        session.setCheckoutEvent(checkoutEvent);
        session.setCheckoutTime(checkoutEvent.getEventTime());
        return sessionRepo.save(session);
    }

    public Optional<ParkingSession> findOpenSession(Long vehicleId) {
        return sessionRepo.findTopByVehicle_IdAndCheckoutTimeIsNullOrderByIdDesc(vehicleId);
    }

    // ========================
    // Flow IN
    // ========================
    @Transactional
    public ParkingSession handleCheckIn(String plateRaw, Long gateId, String snapshotPath) {
        String norm = requireNormalizedPlate(plateRaw);

        Vehicle vehicle = vehicleRepo.findByPlateNo(norm)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + norm));

        if (findOpenSession(vehicle.getId()).isPresent()) {
            throw new IllegalStateException("Vehicle is already checked in (OPEN session): " + norm);
        }

        ParkingEvent inEvent = createEvent(ParkingEvent.EventType.IN, gateId, plateRaw, snapshotPath);

        if (inEvent.getVehicle() == null) {
            inEvent.setVehicle(vehicle);
            eventRepo.save(inEvent);
        }

        return openSession(vehicle, inEvent);
    }

    // ========================
    // Flow OUT
    // ========================
    @Transactional
    public ParkingSession handleCheckOut(String plateRaw, Long gateId, String snapshotPath) {
        String norm = requireNormalizedPlate(plateRaw);

        Vehicle vehicle = vehicleRepo.findByPlateNo(norm)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + norm));

        ParkingSession open = findOpenSession(vehicle.getId())
                .orElseThrow(() -> new IllegalStateException("No OPEN session for vehicle: " + norm));

        ParkingEvent outEvent = createEvent(ParkingEvent.EventType.OUT, gateId, plateRaw, snapshotPath);

        if (outEvent.getVehicle() == null) {
            outEvent.setVehicle(vehicle);
            eventRepo.save(outEvent);
        }

        ParkingSession closed = closeSession(open, outEvent);

        if (closed.getFeeStatus() != ParkingSession.FeeStatus.FREE) {
            BigDecimal fee = feeCalculator.calculate(closed.getCheckinTime(), closed.getCheckoutTime());
            closed.setFeeAmount(fee);

            closed.setFeeStatus(
                    fee.compareTo(BigDecimal.ZERO) == 0
                            ? ParkingSession.FeeStatus.FREE
                            : ParkingSession.FeeStatus.UNPAID
            );

            closed = sessionRepo.save(closed);
        }

        return closed;
    }
}
