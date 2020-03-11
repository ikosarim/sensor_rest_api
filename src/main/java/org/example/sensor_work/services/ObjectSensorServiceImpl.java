package org.example.sensor_work.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sensor_work.entity.ObjectSensorValue;
import org.example.sensor_work.repos.ObjectSensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Service
public class ObjectSensorServiceImpl implements ObjectSensorService {

    @Autowired
    ObjectSensorRepo objectSensorRepo;

    @Override
    @Transactional
    public void readAndSaveData(String filePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<ObjectSensorValue>> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = new FileInputStream(filePath);
        List<ObjectSensorValue> objectSensorValues;
        try {
            objectSensorValues = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            throw new Exception();
        }
        objectSensorRepo.saveAll(objectSensorValues);
    }
//    Необходимо масштабировать чтение файла и маппинг
//    Use batches

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
