package org.example.sensor_work.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Data
@NoArgsConstructor
@Entity
public class ObjectSensorValue {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private Long objectId;
    private Long sensorId;
    private Long time;
    private Double value;

    @Builder
    public ObjectSensorValue(Long objectId, Long sensorId, Long time, Double value) {
        this.objectId = objectId;
        this.sensorId = sensorId;
        this.time = time;
        this.value = value;
    }
}
