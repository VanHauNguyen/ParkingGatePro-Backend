package com.parking.parkingsystem.controller;

import com.parking.parkingsystem.dto.VehicleCreateRequest;
import com.parking.parkingsystem.dto.VehicleResponse;
import com.parking.parkingsystem.service.VehicleAdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleAdminController {

    private final VehicleAdminService service;

    public VehicleAdminController(VehicleAdminService service) {
        this.service = service;
    }

    @PostMapping
    public VehicleResponse create(@RequestBody VehicleCreateRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<VehicleResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public VehicleResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public VehicleResponse update(@PathVariable Long id, @RequestBody VehicleCreateRequest req) {
        return service.updatePlate(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
