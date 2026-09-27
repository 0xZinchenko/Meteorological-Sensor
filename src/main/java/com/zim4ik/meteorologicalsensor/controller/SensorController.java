package com.zim4ik.meteorologicalsensor.controller;

import com.zim4ik.meteorologicalsensor.dto.SensorDTO;
import com.zim4ik.meteorologicalsensor.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors")
@Tag(name = "Sensors", description = "Sensor registration")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/registration")
    @Operation(summary = "Register a new sensor", description = "Sensor name must be unique")
    public ResponseEntity<Void> register(@RequestBody @Valid SensorDTO dto) {
        sensorService.registerSensor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
