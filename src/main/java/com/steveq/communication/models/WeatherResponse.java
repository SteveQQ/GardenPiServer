package com.steveq.communication.models;

import com.steveq.weather.model.WeatherModel;

/**
 * Created by Adam on 2017-08-13.
 */
public class WeatherResponse{
    private String method;
    private WeatherModel weather;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public WeatherModel getWeather() {
        return weather;
    }

    public void setWeather(WeatherModel weather) {
        this.weather = weather;
    }
}
