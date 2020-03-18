package org.example.sensor_work.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.sensor_work.entity.ObjectOfObservation;

@Getter
@Setter
public class LatestAvgForAllObjects {

    private final ObjectOfObservation object;
    private final double latestAvg;

    @Builder
    public LatestAvgForAllObjects(ObjectOfObservation object, double latestAvg) {
        this.object = object;
        this.latestAvg = latestAvg;
    }
}
