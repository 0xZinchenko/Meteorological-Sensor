package com.zim4ik.meteorologicalsensor.client;

import com.zim4ik.meteorologicalsensor.dto.MeasurementDTO;
import com.zim4ik.meteorologicalsensor.dto.SensorDTO;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ThreadLocalRandom;

public class SensorClient {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String SENSOR_REGISTRATION_ENDPOINT = "/sensors/registration";
    private static final String MEASUREMENT_ENDPOINT = "/measurements/add";
    private static final int TOTAL_REQUESTS = 1000;

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        SensorDTO dto = new SensorDTO();
        dto.setName("MySensor-1");

        try {
            restTemplate.postForEntity(BASE_URL + SENSOR_REGISTRATION_ENDPOINT, dto, Void.class);
            System.out.println("Sensor registered successfully");
        } catch (HttpStatusCodeException e) {
            System.out.println(e.getStatusCode() + " " + e.getResponseBodyAsString());
        }

        int successfulRequests = 0;
        int failedRequests = 0;
        for (int i = 0; i < TOTAL_REQUESTS; i++) {

            try {
                MeasurementDTO measurementDTO = new MeasurementDTO();

                measurementDTO.setValue(ThreadLocalRandom.current().nextDouble(-100, 100));
                measurementDTO.setRaining(ThreadLocalRandom.current().nextBoolean());
                measurementDTO.setSensorName(dto.getName());
                restTemplate.postForEntity(BASE_URL + MEASUREMENT_ENDPOINT, measurementDTO, Void.class);
                successfulRequests++;
            } catch (HttpStatusCodeException e) {
                failedRequests++;
            }
            if ((i + 1) % 100 == 0) {
                System.out.println("Attempts: " + (i + 1));
                System.out.println("Success: " + successfulRequests);
                System.out.println("Failed: " + failedRequests);
                System.out.println("Success rate: " + (successfulRequests * 100.0 ) / (i + 1) + "%");
            }
        }

        System.out.println("FINAL REPORT");
        System.out.println("Attempts: " + TOTAL_REQUESTS);
        System.out.println("Success: " + successfulRequests);
        System.out.println("Failed: " + failedRequests);
        System.out.println("Success rate: " + (successfulRequests * 100.0 / TOTAL_REQUESTS) + "%");
    }
}
