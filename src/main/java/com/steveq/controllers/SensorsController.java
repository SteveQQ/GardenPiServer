package com.steveq.controllers;

import com.steveq.communication.JsonProcessor;
import com.steveq.communication.models.*;
import com.steveq.sensors.*;
import com.steveq.sensors.Sensor;
import com.steveq.utils.PinsUtils;
import com.steveq.weather.model.WeatherOutputModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 2017-08-19.
 */
public class SensorsController implements Controller {
    public static List<Sensor> sensors;

    public SensorsController(){
        sensors = new ArrayList<>();
        sensors.add(new DHTHumiditySensor());
        sensors.add(new DHTTemperatureSensor());
        sensors.add(new MoistureSensor(PinsUtils.Input.MOIS1));
        sensors.add(new MoistureSensor(PinsUtils.Input.MOIS2));
        sensors.add(new PotentiometerSensor(PinsUtils.Input.POT1));
        sensors.add(new PotentiometerSensor(PinsUtils.Input.POT2));
        sensors.add(new RaindropSensor());
    }

    @Override
    public ToClientResponse processRequest(FromClientRequest request) {
        for(Sensor sensor : sensors){
            sensor.takeMeasurements();
        }

        List<com.steveq.communication.models.Sensor> sensorsToResponse = new ArrayList<>();

        ToClientResponse response = new ToClientResponse();
        response.setMethod(JsonProcessor.Method.SENSORS.toString());
        response.setSections(new ArrayList<>());
        response.setWeather(new WeatherOutputModel());

        for(Sensor sensor : sensors){
            com.steveq.communication.models.Sensor sensorModel = new com.steveq.communication.models.Sensor();
            sensorModel.setName(sensor.getName());
            sensorModel.setData(sensor.getReadableData());
            sensorsToResponse.add(sensorModel);
        }

        response.setSensors(sensorsToResponse);

        return response;
    }
}
