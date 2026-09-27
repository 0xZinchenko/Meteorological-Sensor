package com.zim4ik.meteorologicalsensor.controller;


import com.zim4ik.meteorologicalsensor.dto.MeasurementDTO;
import com.zim4ik.meteorologicalsensor.service.MeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
@Tag(name = "Measurements", description = "Measurement submission and retrieval")
public class MeasurementController {

    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new measurement for an existing sensor")
    public ResponseEntity<Void> add(@RequestBody @Valid MeasurementDTO dto) {
        measurementService.addMeasurement(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Get all stored measurements")
    public List<MeasurementDTO> getAll() {
        return measurementService.getAllMeasurements();
    }

    @GetMapping("/rainyDaysCount")
    @Operation(summary = "Get the number of measurements recorded as raining")
    public Long getRainyDaysCount() {
        return measurementService.getRainyDaysCount();
    }
}
