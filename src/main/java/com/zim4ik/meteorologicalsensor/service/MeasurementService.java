package com.zim4ik.meteorologicalsensor.service;


import com.zim4ik.meteorologicalsensor.dto.MeasurementDTO;
import com.zim4ik.meteorologicalsensor.models.Measurement;
import com.zim4ik.meteorologicalsensor.models.Sensor;
import com.zim4ik.meteorologicalsensor.repository.MeasurementRepository;
import com.zim4ik.meteorologicalsensor.repository.SensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
                    return new IllegalArgumentException("Sensor with name %s does not exist".formatted(dto.getSensorName()));
                });

        Measurement measurement = new Measurement();
        measurement.setValue(dto.getValue());
        measurement.setRaining(dto.getRaining());
        measurement.setSensor(sensor);
        measurementRepository.save(measurement);
        log.debug("Measurement recorded for sensor '{}': value={}, raining={}", dto.getSensorName(), dto.getValue(), dto.getRaining());
    }

    public Page<MeasurementDTO> getAllMeasurements(Pageable pageable) {
        return measurementRepository.findAll(pageable).map(this::toDto);
    }

    private MeasurementDTO toDto(Measurement m) {
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setValue(m.getValue());
        measurementDTO.setRaining(m.getRaining());
        measurementDTO.setSensorName(m.getSensor().getName());
        measurementDTO.setMeasuredAt(m.getMeasuredAt());
        return measurementDTO;
    }


    public Long getRainyDaysCount() {
        return measurementRepository.countByRainingTrue();
    }


}
