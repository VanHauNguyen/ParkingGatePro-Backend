package com.parking.parkingsystem.service;

import com.parking.parkingsystem.dto.RecentDtos;
import com.parking.parkingsystem.entity.ParkingEvent;
import com.parking.parkingsystem.entity.ParkingSession;
import com.parking.parkingsystem.repository.ParkingEventRepository;
import com.parking.parkingsystem.repository.ParkingSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecentActivityService {

    private final ParkingEventRepository eventRepo;
    private final ParkingSessionRepository sessionRepo;

    public RecentActivityService(ParkingEventRepository eventRepo, ParkingSessionRepository sessionRepo) {
        this.eventRepo = eventRepo;
        this.sessionRepo = sessionRepo;
    }

    public List<RecentDtos.RecentEventResponse> recentEvents(int limit) {
        // quick mapping: 50/100
        List<ParkingEvent> list = (limit > 50) ? eventRepo.findTop100ByOrderByIdDesc()
                : eventRepo.findTop50ByOrderByIdDesc();

        return list.stream().limit(limit).map(this::toEventDto).toList();
    }

    public List<RecentDtos.RecentSessionResponse> recentSessions(int limit) {
        List<ParkingSession> list = (limit > 50) ? sessionRepo.findTop100ByOrderByIdDesc()
                : sessionRepo.findTop50ByOrderByIdDesc();

        return list.stream().limit(limit).map(this::toSessionDto).toList();
    }

    private RecentDtos.RecentEventResponse toEventDto(ParkingEvent e) {
        return new RecentDtos.RecentEventResponse(
                e.getId(),
                e.getEventType() == null ? null : e.getEventType().name(),
                e.getEventTime(),
                e.getGateId(),
                e.getVehicle() == null ? null : e.getVehicle().getId(),
                e.getPlateNoRaw(),
                e.getPlateNoNorm(),
                e.getOcrConfidence(),
                e.getSnapshotPath(),
                e.getStatus() == null ? null : e.getStatus().name(),  // <-- sửa ở đây
                e.getHandledBy(),
                e.getNote()
        );
    }

    private RecentDtos.RecentSessionResponse toSessionDto(ParkingSession s) {
        return new RecentDtos.RecentSessionResponse(
                s.getId(),
                s.getVehicle() == null ? null : s.getVehicle().getId(),
                s.getCheckinEvent() == null ? null : s.getCheckinEvent().getId(),
                s.getCheckoutEvent() == null ? null : s.getCheckoutEvent().getId(),
                s.getCheckinTime(),
                s.getCheckoutTime(),
                s.getFeeAmount(),
                s.getFeeStatus() == null ? null : s.getFeeStatus().name(),
                s.getPaidAt()
        );
    }
}
