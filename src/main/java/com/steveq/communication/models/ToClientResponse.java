package com.steveq.communication.models;

import com.steveq.weather.model.WeatherModel;
import com.steveq.weather.model.WeatherOutputModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 2017-08-03.
 */
public class ToClientResponse{
    private String method;
    private List<Section> payload;
    private WeatherOutputModel weather;
    private List<Sensor> sensors = new ArrayList<>();

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Section> getSections() {
        return payload;
    }

    public void setSections(List<Section> sections) {
        this.payload = sections;
    }

    public WeatherOutputModel getWeather() {
        return weather;
    }

    public void setWeather(WeatherOutputModel weather) {
        this.weather = weather;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
}
