package org.example.sensor_work.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sensor_work.entity.MeasurementResultValue;
import org.example.sensor_work.entity.ObjectOfObservation;
import org.example.sensor_work.entity.Sensor;
import org.example.sensor_work.repos.ObjectOfObservationRepo;
import org.example.sensor_work.repos.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataServiceImpl implements DataService{

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    ObjectOfObservationRepo objectOfObservationRepo;
    @Autowired
    SensorRepo sensorRepo;

    @Override
    public Pair<Map<Long, ObjectOfObservation>, Map<Long, Sensor>> readAndGetData(String filePath) throws Exception {
        TypeReference<List<JsonNode>> typeReference = new TypeReference<>() {
        };
        List<JsonNode> jsonNodes;
        int repeat = 0;
        while (true) {
            try {
                InputStream inputStream = new FileInputStream(filePath);
                jsonNodes = MAPPER.readValue(inputStream, typeReference);
                break;
            } catch (Exception e) {
                if (repeat < 3) {
                    repeat++;
                } else {
                    new File(filePath).delete();
                    throw new Exception();
                }
            }
        }
        Map<Long, ObjectOfObservation> objectOfObservationMap = new HashMap<>() {{
            objectOfObservationRepo.findAll()
                    .stream()
                    .peek(o -> put(o.getObjectId(), o))
                    .count();
        }};
        Map<Long, Sensor> sensorMap = new HashMap<>() {{
            sensorRepo.findAll()
                    .stream()
                    .peek(s -> put(s.getSensorId(), s))
                    .count();
        }};
        for (JsonNode node : jsonNodes) {
            long objectId = node.get("objectId").asLong();
            ObjectOfObservation object = objectOfObservationMap.get(objectId);
            if (object == null) {
                object = ObjectOfObservation.builder()
                        .objectId(objectId)
                        .objectName("")
                        .build();
                objectOfObservationMap.put(objectId, object);
            }
            long sensorId = node.get("sensorId").asLong();
            Sensor sensor = sensorMap.get(sensorId);
            if (sensor == null) {
                sensor = Sensor.builder()
                        .sensorId(sensorId)
                        .sensorName("")
                        .build();
                sensorMap.put(sensorId, sensor);
            }
            MeasurementResultValue measurementResultValue = MeasurementResultValue.builder()
                    .objectOfObservation(object)
                    .sensor(sensor)
                    .time(node.get("time").asLong())
                    .value(node.get("value").asDouble())
                    .build();
            object.getMeasurementResultValues().add(measurementResultValue);
            sensor.getMeasurementResultValues().add(measurementResultValue);
        }
        return Pair.of(objectOfObservationMap, sensorMap);
    }

    @Override
    public File generateData() throws Exception {
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
        return file;
    }
}
