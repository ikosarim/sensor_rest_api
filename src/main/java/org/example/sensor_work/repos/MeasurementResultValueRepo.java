package org.example.sensor_work.repos;

import org.example.sensor_work.entity.MeasurementResultValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeasurementResultValueRepo extends JpaRepository<MeasurementResultValue, Long> {

    @Query("select mrv from MeasurementResultValue mrv " +
            "where mrv.sensor.sensorId = :sensorId " +
            "and mrv.time between :startTime and :endTime " +
            "order by mrv.objectOfObservation.objectId, mrv.time")
    List<MeasurementResultValue> findAllBySensorIdAndTimeBetweenOrderByObjectId(@Param("sensorId") Long sensorId,
                                                                                @Param("startTime") Long startTime,
                                                                                @Param("endTime") Long endTime);

    @Query("select mrv from MeasurementResultValue mrv " +
            "where mrv.time = (select max (tmrv.time) from MeasurementResultValue tmrv)" +
            "and mrv.objectOfObservation.objectId = :objectId " +
            "order by mrv.sensor.sensorId")
    List<MeasurementResultValue> findAllByObjectIdAndMaxTime(@Param("objectId") Long objectId);

    @Query("select mrv.objectOfObservation.objectId, avg (mrv.value) from MeasurementResultValue mrv " +
            "where mrv.time = (select max (tmrv.time) from MeasurementResultValue tmrv)" +
            "group by mrv.objectOfObservation.objectId " +
            "order by mrv.objectOfObservation.objectId")
    Object[][] findLatestAvgForAllObjectsByMaxTime();
}
