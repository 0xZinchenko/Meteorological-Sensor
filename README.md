# 🌦 Meteorological Sensor System

A REST-based weather data platform built with Spring Boot that simulates IoT sensors sending environmental measurements to a backend system. The project includes a load-testing client and data visualization module.

---

## 📌 Overview

The system simulates a meteorological sensor that continuously generates and sends temperature and rainfall data to a backend API. The backend stores this data in a PostgreSQL database and exposes endpoints for retrieval and analytics.

A built-in client application:
- registers a sensor
- sends 1000 measurement requests
- collects success/failure statistics
- retrieves stored data
- visualizes results using XChart

---

## 🧱 Architecture

The project consists of two main components:

### 1. Backend (Spring Boot REST API)
Responsible for:
- Sensor registration
- Receiving measurements
- Data persistence (PostgreSQL)
- Data retrieval and aggregation

### 2. Client Application
Responsible for:
- Simulating sensor behavior
- Sending load (1000 requests via RestTemplate)
- Handling API responses and errors
- Fetching stored data
- Rendering visualization charts

---

## ⚙️ Tech Stack

- Java 21
- Spring Boot
- Spring Web (REST)
- Spring Data JPA (Hibernate)
- PostgreSQL
- RestTemplate (HTTP client)
- XChart (data visualization)
- Jakarta Validation API

---

## 📡 REST API

### Sensor Controller

- `POST /sensors/registration`  
  Registers a new sensor (sensor name must be unique)

---

### Measurement Controller

- `POST /measurements/add`  
  Adds a new measurement

- `GET /measurements`  
  Returns all stored measurements

- `GET /measurements/rainyDaysCount`  
  Returns number of rainy days

---

## 🗄️ Database Schema

### Sensor
- id (PK)
- name (unique)

### Measurement
- id (PK)
- value (temperature)
- raining (boolean)
- measuredAt (timestamp)
- sensor_id (FK)

---
