package org.example.sensor_work.services;

import org.example.sensor_work.entity.MeasurementResultValue;
import org.example.sensor_work.entity.ObjectOfObservation;
import org.example.sensor_work.entity.Sensor;
import org.example.sensor_work.repos.MeasurementResultValueRepo;
import org.example.sensor_work.repos.ObjectOfObservationRepo;
import org.example.sensor_work.repos.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ObjectSensorServiceImpl implements ObjectSensorService {

    @Autowired
    ObjectOfObservationRepo objectOfObservationRepo;
    @Autowired
    SensorRepo sensorRepo;
    @Autowired
    MeasurementResultValueRepo measurementResultValueRepo;

    @Override
    public List<MeasurementResultValue> findHistoryBySensorIdAndTimeBetween(Long sensorId, Long startTime, Long endTime) {
        return measurementResultValueRepo.findAllBySensorIdAndTimeBetweenOrderByObjectId(sensorId, startTime, endTime);
    }

    @Override
    public List<MeasurementResultValue> findAllLatestForObject(Long objectId) {
        return measurementResultValueRepo.findAllByObjectIdAndMaxTime(objectId);
    }

    @Override
    public Object[][] findLatestAvgForAllObjects() {
        return measurementResultValueRepo.findLatestAvgForAllObjectsByMaxTime();
    }

    @Override
    @Transactional
    public void saveSensors(Set<Sensor> sensors) {
        sensorRepo.saveAll(sensors);
    }

    @Override
    @Transactional
    public void saveObjectOfObservationAndValues(Set<ObjectOfObservation> objectsWithValues) {
        objectOfObservationRepo.saveAll(objectsWithValues);
    }
}
