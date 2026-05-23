package com.zim4ik.meteorologicalsensor.service;

import com.zim4ik.meteorologicalsensor.dto.SensorDTO;
import com.zim4ik.meteorologicalsensor.models.Sensor;
import com.zim4ik.meteorologicalsensor.repository.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }


    @Transactional
    public void registerSensor(SensorDTO dto) {
        Optional<Sensor> sensorOptional = sensorRepository.findByName(dto.getName());
        if (sensorOptional.isPresent()) {
            throw new IllegalArgumentException();
        }
        Sensor sensor = new Sensor();
        sensor.setName(dto.getName());
        sensorRepository.save(sensor);

    }
}
