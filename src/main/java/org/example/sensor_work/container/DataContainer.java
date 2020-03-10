package org.example.sensor_work.container;

import lombok.Data;
import org.example.sensor_work.entity.ObjectSensorValue;

import java.util.ArrayList;
import java.util.List;

@Data
public class DataContainer {

    private List<ObjectSensorValue> objectSensorValues = new ArrayList<>();
}
