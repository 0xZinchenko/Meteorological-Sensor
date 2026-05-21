package com.zim4ik.meteorologicalsensor.repository;

import com.zim4ik.meteorologicalsensor.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {

    Optional<Sensor> findByName(String name);
}
