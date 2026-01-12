package com.parking.parkingsystem.repository;

import com.parking.parkingsystem.entity.MonthlyStatus;
import com.parking.parkingsystem.entity.MonthlySubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MonthlySubscriptionRepository extends JpaRepository<MonthlySubscription, Long> {

    List<MonthlySubscription> findByVehicleIdOrderByIdDesc(Long vehicleId);

    @Query("""
        SELECT (COUNT(ms) > 0)
        FROM MonthlySubscription ms
        WHERE ms.vehicleId = :vehicleId
          AND ms.status = :active
          AND ms.startDate <= :today
          AND ms.endDate >= :today
    """)
    boolean hasActiveToday(@Param("vehicleId") Long vehicleId,
                           @Param("today") LocalDate today,
                           @Param("active") MonthlyStatus active);

    @Query("""
        SELECT ms
        FROM MonthlySubscription ms
        WHERE ms.vehicleId = :vehicleId
          AND ms.status = :active
          AND ms.startDate <= :today
          AND ms.endDate >= :today
        ORDER BY ms.id DESC
    """)
    Optional<MonthlySubscription> findActiveToday(@Param("vehicleId") Long vehicleId,
                                                  @Param("today") LocalDate today,
                                                  @Param("active") MonthlyStatus active);
}
