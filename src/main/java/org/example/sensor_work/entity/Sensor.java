package org.example.sensor_work.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Sensor {

    @Id
    private long sensorId;

    @Column(name = "sensor_name")
    private String sensorName;

    @OneToMany(mappedBy = "sensor", fetch = LAZY, orphanRemoval = true)
    private Set<MeasurementResultValue> measurementResultValues;

    @Builder
    public Sensor(long sensorId, String sensorName) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.measurementResultValues = new HashSet<>();
    }
}
