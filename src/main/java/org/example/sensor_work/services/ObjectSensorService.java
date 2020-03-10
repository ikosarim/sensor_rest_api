package org.example.sensor_work.services;

import org.example.sensor_work.entity.ObjectSensorValue;

import java.util.List;
import java.util.Map;

public interface ObjectSensorService {

    void readAndSaveData(String filePath) throws Exception;

    List<ObjectSensorValue> findHistoryBySensorIdAndTimeBetween(Long sensorId, Long startTime, Long endTime);

    List<ObjectSensorValue> findAllLatestForObject(Long objectId);

    Map<Long, Double> findLatestAvgForAllObjects();
}
