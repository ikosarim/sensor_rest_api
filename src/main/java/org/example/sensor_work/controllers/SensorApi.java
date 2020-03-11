package org.example.sensor_work.controllers;

import org.example.sensor_work.facade.SensorApiFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/api")
public class SensorApi {

    @Autowired
    private SensorApiFacade sensorApiFacade;

    @GetMapping
    public String start(Model model) {
        model.addAttribute("historyObjectSensorValues", new ArrayList<>());
        model.addAttribute("latestObjectSensorValues", new ArrayList<>());
        model.addAttribute("avgLatestObjectSensorValues", new HashMap<>());
        return "/api_page";
    }

    @GetMapping("/save")
    public String save(Model model) throws Exception {
        sensorApiFacade.runSaveAndDelJsonData();
        model.addAttribute("historyObjectSensorValues", new ArrayList<>());
        model.addAttribute("latestObjectSensorValues", new ArrayList<>());
        model.addAttribute("avgLatestObjectSensorValues", new HashMap<>());
        return "/api_page";
    }

    @PostMapping("/history")
    public String history(Model model,
                          @RequestParam(value = "sensor_id") Long sensorId,
                          @RequestParam(value = "start_datetime") Long startDatetime,
                          @RequestParam(value = "end_datetime") Long endDatetime) {
        model.addAttribute("historyObjectSensorValues", sensorApiFacade.findAllValuesBySensorIdAndTimeBetween(sensorId, startDatetime, endDatetime));
        model.addAttribute("latestObjectSensorValues", new ArrayList<>());
        model.addAttribute("avgLatestObjectSensorValues", new HashMap<>());
        return "/api_page";
    }

    @PostMapping("/latest")
    public String latest(Model model,
                         @RequestParam(value = "object_id") Long objectId) {
        model.addAttribute("historyObjectSensorValues", new ArrayList<>());
        model.addAttribute("latestObjectSensorValues", sensorApiFacade.findAllLatestForObject(objectId));
        model.addAttribute("avgLatestObjectSensorValues", new HashMap<>());
        return "/api_page";
    }

    @GetMapping("/avg")
    public String avg(Model model) {
        model.addAttribute("historyObjectSensorValues", new ArrayList<>());
        model.addAttribute("latestObjectSensorValues", new ArrayList<>());
        model.addAttribute("avgLatestObjectSensorValues", sensorApiFacade.findLatestAvgForAllObjects());
        return "/api_page";
    }
}
