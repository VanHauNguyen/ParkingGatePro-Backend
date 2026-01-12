package com.parking.parkingsystem.controller;

import com.parking.parkingsystem.dto.MonthlySubCreateRequest;
import com.parking.parkingsystem.dto.MonthlySubResponse;
import com.parking.parkingsystem.service.MonthlySubscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-subscriptions")
public class MonthlySubscriptionController {

    private final MonthlySubscriptionService service;

    public MonthlySubscriptionController(MonthlySubscriptionService service) {
        this.service = service;
    }

    @PostMapping
    public MonthlySubResponse create(@RequestBody MonthlySubCreateRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<MonthlySubResponse> listByVehicle(@RequestParam Long vehicleId) {
        return service.listByVehicle(vehicleId);
    }

    @GetMapping("/active")
    public MonthlySubResponse activeToday(@RequestParam Long vehicleId) {
        return service.getActiveToday(vehicleId);
    }

    @PutMapping("/{id}/cancel")
    public MonthlySubResponse cancel(@PathVariable Long id) {
        return service.cancel(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
