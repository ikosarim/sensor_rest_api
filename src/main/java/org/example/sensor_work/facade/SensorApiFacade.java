package org.example.sensor_work.facade;

import org.example.sensor_work.entity.MeasurementResultValue;
import org.example.sensor_work.entity.ObjectOfObservation;
import org.example.sensor_work.entity.Sensor;
import org.example.sensor_work.services.DataService;
import org.example.sensor_work.services.ObjectSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.asList;

@Component
public class SensorApiFacade {

    @Autowired
    private DataService dataService;
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
        Pair<Map<Long, ObjectOfObservation>, Map<Long, Sensor>> dataPair = dataService.readAndGetData(file.getPath());
        file.delete();
        objectSensorService.saveSensors(new HashSet<>() {{
            addAll(dataPair.getSecond().values());
        }});
        objectSensorService.saveObjectOfObservationAndValues(new HashSet<>() {{
            addAll(dataPair.getFirst().values());
        }});
    }

    public List<MeasurementResultValue> findAllValuesBySensorIdAndTimeBetween(Long sensorId, Long startTime, Long endTime) {
        return objectSensorService.findHistoryBySensorIdAndTimeBetween(sensorId, startTime, endTime);
    }

    public List<MeasurementResultValue> findAllLatestForObject(Long objectId) {
        return objectSensorService.findAllLatestForObject(objectId);
    }

    public Map<Long, Double> findLatestAvgForAllObjects() {
        Object[][] result = objectSensorService.findLatestAvgForAllObjects();
        return new HashMap<>() {{
            asList(result).forEach(o -> put((Long) o[0], (Double) o[1]));
        }};
    }
}
