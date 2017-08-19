package com.steveq.sensors;

/**
 * Created by Adam on 2017-08-19.
 */
public interface Sensor {
    void takeMeasurements();
    double getRawData();
    String getReadableData();
    String getName();
}
