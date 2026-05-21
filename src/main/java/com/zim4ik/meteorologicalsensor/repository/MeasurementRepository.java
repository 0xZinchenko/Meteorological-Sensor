package com.zim4ik.meteorologicalsensor.repository;

import com.zim4ik.meteorologicalsensor.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
