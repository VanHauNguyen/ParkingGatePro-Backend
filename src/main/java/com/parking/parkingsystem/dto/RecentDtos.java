package com.parking.parkingsystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RecentDtos {

    public record RecentEventResponse(
            Long id,
            String eventType,
            LocalDateTime eventTime,
            Long gateId,
            Long vehicleId,
            String plateNoRaw,
            String plateNoNorm,
            BigDecimal ocrConfidence,
            String snapshotPath,
            String status,
            Long handledBy,
            String note
    ) {}

    public record RecentSessionResponse(
            Long id,
            Long vehicleId,
            Long checkinEventId,
            Long checkoutEventId,
            LocalDateTime checkinTime,
            LocalDateTime checkoutTime,
            BigDecimal feeAmount,
            String feeStatus,
            LocalDateTime paidAt
    ) {}
}
