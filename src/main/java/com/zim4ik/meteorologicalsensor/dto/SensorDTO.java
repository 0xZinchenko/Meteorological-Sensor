package com.zim4ik.meteorologicalsensor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
