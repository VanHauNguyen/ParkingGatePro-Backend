package com.parking.parkingsystem.repository;

import com.parking.parkingsystem.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByPlateNo(String plateNo);
    Optional<Vehicle> findByPlateNo(String plateNo);

    boolean existsByPlateNoNorm(String plateNoNorm);
    Optional<Vehicle> findByPlateNoNorm(String plateNoNorm);
}
