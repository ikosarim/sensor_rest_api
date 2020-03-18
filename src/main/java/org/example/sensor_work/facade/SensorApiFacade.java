package org.example.sensor_work.facade;

import org.example.sensor_work.entity.MeasurementResultValue;
import org.example.sensor_work.entity.ObjectOfObservation;
import org.example.sensor_work.entity.Sensor;
import org.example.sensor_work.model.LatestAvgForAllObjects;
import org.example.sensor_work.services.DataService;
import org.example.sensor_work.services.ObjectSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Component
public class SensorApiFacade {

    @Autowired
    private DataService dataService;
    @Autowired
    private ObjectSensorService objectSensorService;

    public void runSaveAndDelJsonData() throws Exception {
        File file = dataService.generateData();
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

    public List<LatestAvgForAllObjects> findLatestAvgForAllObjects() {
        Object[][] result = objectSensorService.findLatestAvgForAllObjects();
        return new ArrayList<>() {{
            asList(result).forEach(
                    o -> add(LatestAvgForAllObjects.builder()
                            .object((objectSensorService.getObjectOfObservationById((Long) o[0])))
                            .latestAvg((Double) o[1])
                            .build())
            );
        }};
    }
}
