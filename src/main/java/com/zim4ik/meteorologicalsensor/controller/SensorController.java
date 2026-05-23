package com.zim4ik.meteorologicalsensor.controller;

import com.zim4ik.meteorologicalsensor.dto.SensorDTO;
import com.zim4ik.meteorologicalsensor.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> register(@RequestBody @Valid SensorDTO dto) {
        sensorService.registerSensor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
