package com.parking.parkingsystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingHistoryDTOs {

    public record EventDTO(
            Long id,
            String eventType,
            LocalDateTime eventTime,
            Integer gateId,
            Long vehicleId,
            String plateNoRaw,
            String plateNoNorm,
            String status,
            String note
    ) {}

    public record SessionDTO(
            Long id,
            Long vehicleId,
            String plateNoNorm,
            Long checkinEventId,
            Long checkoutEventId,
            LocalDateTime checkinTime,
            LocalDateTime checkoutTime,
            BigDecimal feeAmount,
            String feeStatus
    ) {}
}
