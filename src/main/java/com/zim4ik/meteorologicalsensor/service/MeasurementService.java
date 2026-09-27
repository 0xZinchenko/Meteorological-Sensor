package com.zim4ik.meteorologicalsensor.service;


import com.zim4ik.meteorologicalsensor.dto.MeasurementDTO;
import com.zim4ik.meteorologicalsensor.models.Measurement;
import com.zim4ik.meteorologicalsensor.models.Sensor;
import com.zim4ik.meteorologicalsensor.repository.MeasurementRepository;
import com.zim4ik.meteorologicalsensor.repository.SensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class MeasurementService {

    private static final Logger log = LoggerFactory.getLogger(MeasurementService.class);

    private final SensorRepository sensorRepository;
    private final MeasurementRepository measurementRepository;


    public MeasurementService(SensorRepository sensorRepository, MeasurementRepository measurementRepository) {
        this.sensorRepository = sensorRepository;
        this.measurementRepository = measurementRepository;
    }

    @Transactional
    public void addMeasurement(MeasurementDTO dto) {
        Sensor sensor = sensorRepository.findByName(dto.getSensorName())
                .orElseThrow(() -> {
                    log.warn("Measurement rejected: sensor '{}' does not exist", dto.getSensorName());
                    return new IllegalArgumentException("Сенсор с именем %s не существует".formatted(dto.getSensorName()));
                });

        Measurement measurement = new Measurement();
        measurement.setValue(dto.getValue());
        measurement.setRaining(dto.getRaining());
        measurement.setSensor(sensor);
        measurementRepository.save(measurement);
        log.debug("Measurement recorded for sensor '{}': value={}, raining={}", dto.getSensorName(), dto.getValue(), dto.getRaining());
    }

    public List<MeasurementDTO> getAllMeasurements() {
        List<Measurement> measurements = measurementRepository.findAll();

        List<MeasurementDTO> dto = new ArrayList<>();

        for (Measurement m : measurements) {
            MeasurementDTO measurementDTO = new MeasurementDTO();
            measurementDTO.setValue(m.getValue());
            measurementDTO.setRaining(m.getRaining());
            measurementDTO.setSensorName(m.getSensor().getName());
            measurementDTO.setMeasuredAt(m.getMeasuredAt());
            dto.add(measurementDTO);
        }
        return dto;
    }


    public Long getRainyDaysCount() {
        return measurementRepository.countByRainingTrue();
    }


}
