package com.parking.parkingsystem.service;

import com.parking.parkingsystem.dto.VehicleCreateRequest;
import com.parking.parkingsystem.dto.VehicleResponse;
import com.parking.parkingsystem.entity.Vehicle;
import com.parking.parkingsystem.repository.VehicleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VehicleAdminService {

    private final VehicleRepository vehicleRepository;

    public VehicleAdminService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public VehicleResponse create(VehicleCreateRequest req) {
        String norm = normalize(req.getPlateNo());
        if (norm.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid plate");
        }
        if (vehicleRepository.existsByPlateNo(norm)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Vehicle already exists: " + norm);
        }

        Vehicle v = new Vehicle();
        v.setPlateNo(norm);

        Vehicle saved = vehicleRepository.save(v);
        return toResp(saved);
    }

    @Transactional(readOnly = true)
    public List<VehicleResponse> list() {
        return vehicleRepository.findAll()
                .stream()
                .map(this::toResp)
                .toList();
    }

    @Transactional(readOnly = true)
    public VehicleResponse get(Long id) {
        Vehicle v = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found: " + id));
        return toResp(v);
    }

    @Transactional
    public VehicleResponse updatePlate(Long id, VehicleCreateRequest req) {
        Vehicle v = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found: " + id));

        String norm = normalize(req.getPlateNo());
        if (norm.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid plate");
        }

        if (!norm.equals(v.getPlateNo()) && vehicleRepository.existsByPlateNo(norm)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Vehicle already exists: " + norm);
        }

        v.setPlateNo(norm);
        Vehicle saved = vehicleRepository.save(v);
        return toResp(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found: " + id);
        }
        vehicleRepository.deleteById(id);
    }

    private VehicleResponse toResp(Vehicle v) {
        //  FIX: createdAt giờ lấy được vì entity có getCreatedAt()
        return new VehicleResponse(v.getId(), v.getPlateNo(), v.getCreatedAt());
    }

    private String normalize(String raw) {
        if (raw == null) return "";
        return raw.trim()
                .replaceAll("[^A-Za-z0-9]", "")
                .toUpperCase();
    }
}
