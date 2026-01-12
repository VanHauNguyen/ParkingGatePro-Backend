package com.parking.parkingsystem.service;

import com.parking.parkingsystem.config.ParkingFeeProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class FeeCalculatorService {

    private final ParkingFeeProperties props;

    public FeeCalculatorService(ParkingFeeProperties props) {
        this.props = props;
    }

    /**
     * Tính phí gửi xe
     */
    public BigDecimal calculate(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkIn == null || checkOut == null || checkOut.isBefore(checkIn)) {
            return BigDecimal.ZERO;
        }

        long minutes = Duration.between(checkIn, checkOut).toMinutes();

        // miễn phí X phút đầu
        if (minutes <= props.getFreeMinutes()) {
            return BigDecimal.ZERO;
        }

        long chargeMinutes = minutes - props.getFreeMinutes();

        // làm tròn lên theo giờ
        long hours = (long) Math.ceil(chargeMinutes / 60.0);

        BigDecimal fee = props.getRatePerHour()
                .multiply(BigDecimal.valueOf(hours));

        // áp trần ngày (nếu muốn bật)
        if (props.getDailyCap() != null && fee.compareTo(props.getDailyCap()) > 0) {
            fee = props.getDailyCap();
        }

        return fee;
    }
}
