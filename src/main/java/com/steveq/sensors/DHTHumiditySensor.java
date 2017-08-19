package com.steveq.sensors;

import se.hirt.w1.Sensors;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Adam on 2017-08-19.
 */
public class DHTHumiditySensor implements Sensor{
    private static final String NAME = "hygrometer";
    private double data;

    public DHTHumiditySensor(){
        takeMeasurements();
    }

    @Override
    public void takeMeasurements() {
        try {
            Set<se.hirt.w1.Sensor> sensors = Sensors.getSensors();
            System.out.println(String.format("Found %d sensors!", sensors.size()));
            for (se.hirt.w1.Sensor sensor : sensors) {
                if(sensor.getID().contains(NAME)){
                    System.out.println(String.format("%s(%s):%3.2f%s", sensor.getPhysicalQuantity(),
                            sensor.getID(), sensor.getValue(), sensor.getUnitString()));
                    data = Double.valueOf(sensor.getValue().toString());
                    break;
                }
            }
            System.out.println("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public double getRawData() {
        return data;
    }

    @Override
    public String getReadableData() {
        StringBuilder builder = new StringBuilder();
        builder.append(data);
        builder.append("%");
        return builder.toString();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
