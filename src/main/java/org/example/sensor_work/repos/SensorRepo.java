package org.example.sensor_work.repos;

import org.example.sensor_work.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepo extends JpaRepository<Sensor, Long> {
}
