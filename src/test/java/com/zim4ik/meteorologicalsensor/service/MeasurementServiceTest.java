package com.zim4ik.meteorologicalsensor.service;

import com.zim4ik.meteorologicalsensor.dto.MeasurementDTO;
import com.zim4ik.meteorologicalsensor.models.Measurement;
import com.zim4ik.meteorologicalsensor.models.Sensor;
import com.zim4ik.meteorologicalsensor.repository.MeasurementRepository;
import com.zim4ik.meteorologicalsensor.repository.SensorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeasurementServiceTest {

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private MeasurementRepository measurementRepository;

    private MeasurementService measurementService;

    @BeforeEach
    void setUp() {
        measurementService = new MeasurementService(sensorRepository, measurementRepository);
    }

    @Test
    void addMeasurement_savesMeasurement_whenSensorExists() {
        Sensor sensor = new Sensor("MySensor-1");
        MeasurementDTO dto = new MeasurementDTO();
        dto.setSensorName("MySensor-1");
        dto.setValue(21.5);
        dto.setRaining(true);
        when(sensorRepository.findByName("MySensor-1")).thenReturn(Optional.of(sensor));

        measurementService.addMeasurement(dto);

        ArgumentCaptor<Measurement> captor = ArgumentCaptor.forClass(Measurement.class);
        verify(measurementRepository).save(captor.capture());
        Measurement saved = captor.getValue();
        assertThat(saved.getValue()).isEqualTo(21.5);
        assertThat(saved.getRaining()).isTrue();
        assertThat(saved.getSensor()).isEqualTo(sensor);
    }

    @Test
    void addMeasurement_throws_whenSensorDoesNotExist() {
        MeasurementDTO dto = new MeasurementDTO();
        dto.setSensorName("Unknown");
        when(sensorRepository.findByName("Unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> measurementService.addMeasurement(dto))
                .isInstanceOf(IllegalArgumentException.class);

        verify(measurementRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void getAllMeasurements_mapsEntitiesToDtos() {
        Sensor sensor = new Sensor("MySensor-1");
        Measurement measurement = new Measurement();
        measurement.setValue(10.0);
        measurement.setRaining(false);
        measurement.setSensor(sensor);
        measurement.prePersist();
        Pageable pageable = PageRequest.of(0, 20);
        Page<Measurement> page = new PageImpl<>(List.of(measurement), pageable, 1);
        when(measurementRepository.findAll(pageable)).thenReturn(page);

        Page<MeasurementDTO> result = measurementService.getAllMeasurements(pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        MeasurementDTO resultDto = result.getContent().get(0);
        assertThat(resultDto.getValue()).isEqualTo(10.0);
        assertThat(resultDto.getRaining()).isFalse();
        assertThat(resultDto.getSensorName()).isEqualTo("MySensor-1");
        assertThat(resultDto.getMeasuredAt()).isNotNull();
    }

    @Test
    void getRainyDaysCount_delegatesToRepository() {
        when(measurementRepository.countByRainingTrue()).thenReturn(5L);

        Long count = measurementService.getRainyDaysCount();

        assertThat(count).isEqualTo(5L);
    }
}
