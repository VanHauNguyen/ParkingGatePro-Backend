package com.parking.parkingsystem.controller;

import com.parking.parkingsystem.dto.ParkingInRequest;
import com.parking.parkingsystem.dto.ParkingInResponse;
import com.parking.parkingsystem.dto.ParkingOutRequest;
import com.parking.parkingsystem.dto.ParkingOutResponse;
import com.parking.parkingsystem.entity.ParkingSession;
import com.parking.parkingsystem.service.ParkingFlowService;
import com.parking.parkingsystem.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingFlowService flowService;

    public ParkingController(ParkingService parkingService, ParkingFlowService flowService) {
        this.parkingService = parkingService;
        this.flowService = flowService;
    }

    // =========================
    // GET /api/parking/check-monthly
    // =========================
    @GetMapping("/check-monthly")
    public Map<String, Object> checkMonthly(@RequestParam String plate) {
        boolean active = parkingService.hasActiveMonthly(plate);
        return Map.of("plate", plate, "monthlyActive", active);
    }

    // =========================
    // POST /api/parking/in
    // =========================
    @PostMapping("/in")
    public ResponseEntity<?> checkIn(@Valid @RequestBody ParkingInRequest req) {
        try {
            ParkingSession session = flowService.handleCheckIn(
                    req.getPlateNo(),
                    req.getGateId(),
                    req.getSnapshotPath()
            );

            var inEvent = session.getCheckinEvent();
            boolean monthlyFree = session.getFeeStatus().name().equals("FREE");

            ParkingInResponse res = new ParkingInResponse(
                    inEvent.getId(),
                    session.getId(),
                    inEvent.getPlateNoRaw(),
                    inEvent.getPlateNoNorm(),
                    monthlyFree,
                    session.getFeeStatus().name(),
                    session.getCheckinTime()
            );

            return ResponseEntity.ok(res);

        } catch (IllegalArgumentException bad) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", bad.getMessage()));
        } catch (IllegalStateException conflict) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", conflict.getMessage()));
        } catch (RuntimeException notFound) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", notFound.getMessage()));
        }

    }

    // =========================
    // POST /api/parking/out
    // =========================
    @PostMapping("/out")
    public ResponseEntity<?> checkOut(@Valid @RequestBody ParkingOutRequest req) {
        try {
            ParkingSession session = flowService.handleCheckOut(
                    req.getPlateNo(),
                    req.getGateId(),
                    req.getSnapshotPath()
            );

            var outEvent = session.getCheckoutEvent();

            ParkingOutResponse res = new ParkingOutResponse(
                    outEvent.getId(),
                    session.getId(),
                    outEvent.getPlateNoRaw(),
                    outEvent.getPlateNoNorm(),
                    session.getCheckinTime(),
                    session.getCheckoutTime(),
                    session.getFeeStatus().name(),
                    session.getFeeAmount()
            );

            return ResponseEntity.ok(res);

        } catch (IllegalArgumentException bad) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", bad.getMessage()));
        } catch (IllegalStateException conflict) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", conflict.getMessage()));
        } catch (RuntimeException notFound) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", notFound.getMessage()));
        }

    }
}
