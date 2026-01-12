package com.parking.parkingsystem.repository;

import com.parking.parkingsystem.entity.ParkingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingEventRepository extends JpaRepository<ParkingEvent, Long> {

    // recent by id desc
    List<ParkingEvent> findTop50ByOrderByIdDesc();
    List<ParkingEvent> findTop100ByOrderByIdDesc();
}
