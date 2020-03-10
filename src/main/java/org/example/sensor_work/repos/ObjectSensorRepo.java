package org.example.sensor_work.repos;

import org.example.sensor_work.entity.ObjectSensorValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ObjectSensorRepo extends JpaRepository<ObjectSensorValue, Long> {

    List<ObjectSensorValue> findAllBySensorIdAndTimeBetween(Long sensorId, Long startTime, Long endTime);

    @Query("select os from ObjectSensorValue os " +
            "where os.time = (select max (ios.time) from ObjectSensorValue ios)" +
            "and os.objectId = :objectId")
    List<ObjectSensorValue> findAllByObjectIdAndMaxTime(@Param("objectId") Long objectId);

//    @Query
//    Object[][] findLatestAvgForAllObjectsByMaxTime();
}
