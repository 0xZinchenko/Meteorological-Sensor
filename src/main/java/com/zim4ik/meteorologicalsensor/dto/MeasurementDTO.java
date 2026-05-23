package com.zim4ik.meteorologicalsensor.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class MeasurementDTO {

    @NotNull(message = "Value cannot be null")
    @DecimalMin("-100.0")
    @DecimalMax("100.0")
    private Double value;

    @NotNull(message = "Raining cannot be null")
    private Boolean raining;

    private LocalDateTime measuredAt;

    private String sensorName;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public LocalDateTime getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(LocalDateTime measuredAt) {
        this.measuredAt = measuredAt;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
}
