package org.example.sensor_work.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MeasurementResultValue {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "object_id", nullable = false)
    private ObjectOfObservation objectOfObservation;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    @Column(name = "time", nullable = false)
    private Long time;

    @Column(name = "value", nullable = false)
    private Double value;

    @Builder
    public MeasurementResultValue(ObjectOfObservation objectOfObservation, Sensor sensor, Long time, Double value) {
        this.objectOfObservation = objectOfObservation;
        this.sensor = sensor;
        this.time = time;
        this.value = value;
    }
}
