package com.zim4ik.meteorologicalsensor.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Double value;

    private Boolean raining;

    private LocalDateTime measuredAt;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @PrePersist
    public void prePersist() {
        this.measuredAt = LocalDateTime.now();
    }

    public Measurement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
