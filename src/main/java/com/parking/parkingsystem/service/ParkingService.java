package com.parking.parkingsystem.service;

import com.parking.parkingsystem.entity.MonthlyStatus;
import com.parking.parkingsystem.entity.Vehicle;
import com.parking.parkingsystem.repository.MonthlySubscriptionRepository;
import com.parking.parkingsystem.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ParkingService {

    private final VehicleRepository vehicleRepo;
    private final MonthlySubscriptionRepository subRepo;

    public ParkingService(VehicleRepository vehicleRepo,
                          MonthlySubscriptionRepository subRepo) {
        this.vehicleRepo = vehicleRepo;
        this.subRepo = subRepo;
    }

    public boolean hasActiveMonthly(String plateNorm) {
        return vehicleRepo.findByPlateNoNorm(plateNorm)
                .map(v -> subRepo.hasActiveToday(v.getId(), LocalDate.now(), MonthlyStatus.ACTIVE))
                .orElse(false);
    }


}
