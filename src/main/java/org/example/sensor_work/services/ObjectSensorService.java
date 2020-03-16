package org.example.sensor_work.services;

import org.example.sensor_work.entity.MeasurementResultValue;
import org.example.sensor_work.entity.ObjectOfObservation;
import org.example.sensor_work.entity.Sensor;

import java.util.List;
import java.util.Set;

public interface ObjectSensorService {

    List<MeasurementResultValue> findHistoryBySensorIdAndTimeBetween(Long sensorId, Long startTime, Long endTime);

    List<MeasurementResultValue> findAllLatestForObject(Long objectId);

    Object[][] findLatestAvgForAllObjects();

    void saveSensors(Set<Sensor> sensors);

    void saveObjectOfObservationAndValues(Set<ObjectOfObservation> objectsWithValues);
}
