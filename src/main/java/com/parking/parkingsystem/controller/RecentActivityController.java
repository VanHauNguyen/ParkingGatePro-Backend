package com.parking.parkingsystem.controller;

import com.parking.parkingsystem.dto.RecentDtos;
import com.parking.parkingsystem.service.RecentActivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class RecentActivityController {

    private final RecentActivityService service;

    public RecentActivityController(RecentActivityService service) {
        this.service = service;
    }

    // GET /api/parking/events/recent?limit=50
    @GetMapping("/events/recent")
    public List<RecentDtos.RecentEventResponse> recentEvents(
            @RequestParam(defaultValue = "50") int limit
    ) {
        limit = clamp(limit, 1, 200);
        return service.recentEvents(limit);
    }

    // GET /api/parking/sessions/recent?limit=50
    @GetMapping("/sessions/recent")
    public List<RecentDtos.RecentSessionResponse> recentSessions(
            @RequestParam(defaultValue = "50") int limit
    ) {
        limit = clamp(limit, 1, 200);
        return service.recentSessions(limit);
    }

    private int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }
}
