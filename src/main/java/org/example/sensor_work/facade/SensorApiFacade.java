package org.example.sensor_work.facade;

import org.example.sensor_work.entity.ObjectSensorValue;
import org.example.sensor_work.services.ObjectSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Component
public class SensorApiFacade {

    @Autowired
    private ObjectSensorService objectSensorService;

    public void runSaveAndDelJsonData() throws Exception {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("src/main/resources/generate_sensor_data.py");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("data.json");
        long fileSize = file.length();
        boolean writinig = true;
        while (writinig) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            file = new File("data.json");
            if (file.length() == 0) {
                throw new Exception("Problem with writing file");
            }
            if (file.length() > fileSize) {
                fileSize = file.length();
            } else {
                writinig = false;
            }
        }
        objectSensorService.readAndSaveData(file.getPath());
        file.delete();
    }

    public List<ObjectSensorValue> findAllValuesBySensorIdAndTimeBetween(Long sensorId, Long startTime, Long endTime) {
        return objectSensorService.findHistoryBySensorIdAndTimeBetween(sensorId, startTime, endTime);
    }

    public List<ObjectSensorValue> findAllLatestForObject(Long objectId) {
        return objectSensorService.findAllLatestForObject(objectId);
    }

    public Map<Long, Double> findLatestAvgForAllObjects() {
        Object[][] result = objectSensorService.findLatestAvgForAllObjects();
        return new HashMap<>() {{
            asList(result).forEach(o -> put((Long) o[0], (Double) o[1]));
        }};
    }
}
