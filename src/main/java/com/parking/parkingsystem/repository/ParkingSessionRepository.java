package com.parking.parkingsystem.repository;

import com.parking.parkingsystem.entity.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {

    // dùng cho flow IN/OUT: tìm session OPEN của 1 xe
    Optional<ParkingSession> findTopByVehicle_IdAndCheckoutTimeIsNullOrderByIdDesc(Long vehicleId);

    // recent by id desc
    List<ParkingSession> findTop50ByOrderByIdDesc();
    List<ParkingSession> findTop100ByOrderByIdDesc();
}
