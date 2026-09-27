# 🌦 Meteorological Sensor System

[![CI](https://github.com/0xZinchenko/Meteorological-Sensor/actions/workflows/ci.yml/badge.svg)](https://github.com/0xZinchenko/Meteorological-Sensor/actions/workflows/ci.yml)

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

## 🚀 Quick Start

**Prerequisites:** Java 17+, Maven, a running PostgreSQL instance.

```bash
# 1. Create the database
createdb meteorological_sensor
# (or manually: CREATE DATABASE meteorological_sensor;)

# 2. Run the backend (uses localhost:5432/postgres/postgres by default,
#    override with DB_URL / DB_USERNAME / DB_PASSWORD env vars if needed)
./mvnw spring-boot:run

# 3. Open the interactive API docs
open http://localhost:8080/swagger-ui.html

# 4. (Optional) Run the load-test client against the running backend
#    (run SensorClient's main method from your IDE, or build the jar and run it with java -cp)
```

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

- Java 17
- Spring Boot
- Spring Web (REST)
- Spring Data JPA (Hibernate)
- PostgreSQL
- RestTemplate (HTTP client)
- XChart (data visualization)
- Jakarta Validation API
- springdoc-openapi (Swagger UI)

---

## 📡 REST API

Interactive documentation (Swagger UI) is available once the backend is running at:
`http://localhost:8080/swagger-ui.html`
(raw OpenAPI spec at `http://localhost:8080/v3/api-docs`)

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

## ⚙️ Configuration

Database connection settings can be overridden via environment variables (defaults are for local development):

| Variable      | Default                                              |
|---------------|-------------------------------------------------------|
| `DB_URL`      | `jdbc:postgresql://localhost:5432/meteorological_sensor` |
| `DB_USERNAME` | `postgres`                                            |
| `DB_PASSWORD` | `postgres`                                            |

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

## 📄 License

This project is licensed under the [MIT License](LICENSE).
