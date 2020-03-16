package org.example.sensor_work.services;

import org.example.sensor_work.entity.ObjectOfObservation;
import org.example.sensor_work.entity.Sensor;
import org.springframework.data.util.Pair;

import java.io.File;
import java.util.Map;

public interface DataService {

    Pair<Map<Long, ObjectOfObservation>, Map<Long, Sensor>> readAndGetData(String filePath) throws Exception;

    File generateData() throws Exception;
}
