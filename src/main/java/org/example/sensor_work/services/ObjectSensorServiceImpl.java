package org.example.sensor_work.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sensor_work.entity.ObjectSensorValue;
import org.example.sensor_work.repos.ObjectSensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Service
public class ObjectSensorServiceImpl implements ObjectSensorService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    ObjectSensorRepo objectSensorRepo;

    @Override
    @Transactional
    public void readAndSaveData(String filePath) throws Exception {
        long startTime = System.currentTimeMillis();
        TypeReference<List<ObjectSensorValue>> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = new FileInputStream(filePath);
        List<ObjectSensorValue> objectSensorValues;
        try {
            objectSensorValues = MAPPER.readValue(inputStream, typeReference);
        } catch (Exception e) {
            throw new Exception();
        }
        System.out.println(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        objectSensorRepo.saveAll(objectSensorValues);
        System.out.println(System.currentTimeMillis() - startTime);
    }

    @Override
    public List<ObjectSensorValue> findHistoryBySensorIdAndTimeBetween(Long sensorId, Long startTime, Long endTime) {
        return objectSensorRepo.findAllBySensorIdAndTimeBetweenOrderByObjectId(sensorId, startTime, endTime);
    }

    @Override
    public List<ObjectSensorValue> findAllLatestForObject(Long objectId) {
        return objectSensorRepo.findAllByObjectIdAndMaxTime(objectId);
    }

    @Override
    public Object[][] findLatestAvgForAllObjects() {
        return objectSensorRepo.findLatestAvgForAllObjectsByMaxTime();
    }
}
