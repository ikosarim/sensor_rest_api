package org.example.sensor_work.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ObjectOfObservation {

    @Id
    private long objectId;

    @Column(name = "object_name")
    private String objectName;

    @JsonIgnore
    @OneToMany(mappedBy = "objectOfObservation", cascade = ALL, fetch = LAZY, orphanRemoval = true)
    private Set<MeasurementResultValue> measurementResultValues;

    @Builder
    public ObjectOfObservation(long objectId, String objectName) {
        this.objectId = objectId;
        this.objectName = objectName;
        this.measurementResultValues = new HashSet<>();
    }
}
