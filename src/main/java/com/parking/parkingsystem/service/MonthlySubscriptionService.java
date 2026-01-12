package com.parking.parkingsystem.service;

import com.parking.parkingsystem.dto.MonthlySubCreateRequest;
import com.parking.parkingsystem.dto.MonthlySubResponse;
import com.parking.parkingsystem.entity.MonthlyStatus;
import com.parking.parkingsystem.entity.MonthlySubscription;
import com.parking.parkingsystem.repository.MonthlySubscriptionRepository;
import com.parking.parkingsystem.repository.VehicleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class MonthlySubscriptionService {

    private final MonthlySubscriptionRepository repo;
    private final VehicleRepository vehicleRepo;

    public MonthlySubscriptionService(MonthlySubscriptionRepository repo, VehicleRepository vehicleRepo) {
        this.repo = repo;
        this.vehicleRepo = vehicleRepo;
    }

    @Transactional
    public MonthlySubResponse create(MonthlySubCreateRequest req) {
        if (req.getVehicleId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vehicleId is required");
        }
        if (!vehicleRepo.existsById(req.getVehicleId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found: " + req.getVehicleId());
        }

        LocalDate start = parseDate(req.getStartDate(), "startDate");
        LocalDate end = parseDate(req.getEndDate(), "endDate");

        if (end.isBefore(start)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endDate must be >= startDate");
        }

        // Rule: không cho 2 vé ACTIVE chồng ngày "hôm nay"
        LocalDate today = LocalDate.now();
        if (repo.hasActiveToday(req.getVehicleId(), today, MonthlyStatus.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Vehicle already has active monthly today");
        }

        MonthlySubscription ms = new MonthlySubscription();
        ms.setVehicleId(req.getVehicleId());
        ms.setStartDate(start);
        ms.setEndDate(end);
        if (req.getPlanName() != null && !req.getPlanName().isBlank()) ms.setPlanName(req.getPlanName());
        if (req.getPrice() != null) ms.setPrice(req.getPrice());

        //  enum
        ms.setStatus(MonthlyStatus.ACTIVE);

        MonthlySubscription saved = repo.save(ms);
        return toResp(saved);
    }

    @Transactional(readOnly = true)
    public List<MonthlySubResponse> listByVehicle(Long vehicleId) {
        if (vehicleId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vehicleId is required");
        }
        return repo.findByVehicleIdOrderByIdDesc(vehicleId)
                .stream()
                .map(this::toResp)
                .toList();
    }

    @Transactional(readOnly = true)
    public MonthlySubResponse getActiveToday(Long vehicleId) {
        if (vehicleId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vehicleId is required");
        }

        return repo.findActiveToday(vehicleId, LocalDate.now(), MonthlyStatus.ACTIVE)
                .map(this::toResp)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No active monthly today"));
    }

    @Transactional
    public MonthlySubResponse cancel(Long id) {
        MonthlySubscription ms = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Monthly not found: " + id));

        //  enum
        ms.setStatus(MonthlyStatus.SUSPENDED);

        MonthlySubscription saved = repo.save(ms);
        return toResp(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Monthly not found: " + id);
        }
        repo.deleteById(id);
    }

    private MonthlySubResponse toResp(MonthlySubscription ms) {
        return new MonthlySubResponse(
                ms.getId(),
                ms.getVehicleId(),
                ms.getStartDate() == null ? null : ms.getStartDate().toString(),
                ms.getEndDate() == null ? null : ms.getEndDate().toString(),
                ms.getStatus() == null ? null : ms.getStatus().name(),
                ms.getPlanName(),
                ms.getPrice()
        );
    }

    private LocalDate parseDate(String s, String fieldName) {
        if (s == null || s.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " is required (YYYY-MM-DD)");
        }
        try {
            return LocalDate.parse(s.trim());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " invalid format (YYYY-MM-DD)");
        }
    }
}
