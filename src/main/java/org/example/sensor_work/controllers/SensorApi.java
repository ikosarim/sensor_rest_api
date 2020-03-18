package org.example.sensor_work.controllers;

import org.example.sensor_work.entity.MeasurementResultValue;
import org.example.sensor_work.facade.SensorApiFacade;
import org.example.sensor_work.model.LatestAvgForAllObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class SensorApi {

    @Autowired
    private SensorApiFacade sensorApiFacade;

    @GetMapping("/save")
    @ResponseStatus(value = HttpStatus.OK)
    public void save() throws Exception {
        sensorApiFacade.runSaveAndDelJsonData();
    }

    @GetMapping("/history")
    public ResponseEntity<List<MeasurementResultValue>> history(@RequestParam(value = "sensor_id") Long sensorId,
                                                                @RequestParam(value = "start_datetime") Long startDatetime,
                                                                @RequestParam(value = "end_datetime") Long endDatetime) {
        List<MeasurementResultValue> history = sensorApiFacade.findAllValuesBySensorIdAndTimeBetween(sensorId, startDatetime, endDatetime);
        if (history.isEmpty()) {
            return new ResponseEntity<>(NO_CONTENT);
        }
        return new ResponseEntity<>(history, OK);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<MeasurementResultValue>> latest(@RequestParam(value = "object_id") Long objectId) {
        List<MeasurementResultValue> latest = sensorApiFacade.findAllLatestForObject(objectId);
        if (latest.isEmpty()) {
            return new ResponseEntity<>(NO_CONTENT);
        }
        return new ResponseEntity<>(latest, OK);
    }

    @GetMapping("/avg")
    public ResponseEntity<List<LatestAvgForAllObjects>> avg() {
        List<LatestAvgForAllObjects> avg = sensorApiFacade.findLatestAvgForAllObjects();
        if (avg.isEmpty()) {
            return new ResponseEntity<>(NO_CONTENT);
        }
        return new ResponseEntity<>(avg, OK);
    }
}
