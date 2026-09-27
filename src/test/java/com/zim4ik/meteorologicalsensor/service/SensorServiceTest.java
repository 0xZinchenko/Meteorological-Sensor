package com.zim4ik.meteorologicalsensor.service;

import com.zim4ik.meteorologicalsensor.dto.SensorDTO;
import com.zim4ik.meteorologicalsensor.models.Sensor;
import com.zim4ik.meteorologicalsensor.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SensorServiceTest {

    @Mock
    private SensorRepository sensorRepository;

    private SensorService sensorService;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        sensorService = new SensorService(sensorRepository);
    }

    @Test
    void registerSensor_savesNewSensor_whenNameIsUnique() {
        SensorDTO dto = new SensorDTO();
        dto.setName("MySensor-1");
        when(sensorRepository.findByName("MySensor-1")).thenReturn(Optional.empty());

        sensorService.registerSensor(dto);

        ArgumentCaptor<Sensor> captor = ArgumentCaptor.forClass(Sensor.class);
        verify(sensorRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("MySensor-1");
    }

    @Test
    void registerSensor_propagatesDataIntegrityViolation_onConcurrentDuplicateInsert() {
        SensorDTO dto = new SensorDTO();
        dto.setName("MySensor-1");
        when(sensorRepository.findByName("MySensor-1")).thenReturn(Optional.empty());
        when(sensorRepository.save(org.mockito.ArgumentMatchers.any()))
                .thenThrow(new org.springframework.dao.DataIntegrityViolationException("duplicate key"));

        assertThatThrownBy(() -> sensorService.registerSensor(dto))
                .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
    }

    @Test
    void registerSensor_throws_whenNameAlreadyExists() {
        SensorDTO dto = new SensorDTO();
        dto.setName("MySensor-1");
        when(sensorRepository.findByName("MySensor-1")).thenReturn(Optional.of(new Sensor("MySensor-1")));

        assertThatThrownBy(() -> sensorService.registerSensor(dto))
                .isInstanceOf(IllegalArgumentException.class);

        verify(sensorRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void getAllSensors_mapsEntitiesToDtos() {
        when(sensorRepository.findAll()).thenReturn(List.of(new Sensor("MySensor-1"), new Sensor("MySensor-2")));

        List<SensorDTO> result = sensorService.getAllSensors();

        assertThat(result).extracting(SensorDTO::getName)
                .containsExactly("MySensor-1", "MySensor-2");
    }
}
