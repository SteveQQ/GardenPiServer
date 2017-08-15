package com.steveq.weather.model;

import com.steveq.weather.model.WeatherCurrentlyModel;

/**
 * Created by Adam on 2017-08-13.
 */
public class WeatherModel {
    private Double latitude;
    private Double longitude;
    private WeatherCurrentlyModel currently;
    private WeatherHourlyModel hourly;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public WeatherCurrentlyModel getCurrently() {
        return currently;
    }

    public void setCurrently(WeatherCurrentlyModel currently) {
        this.currently = currently;
    }

    public WeatherHourlyModel getHourly() {
        return hourly;
    }

    public void setHourly(WeatherHourlyModel hourly) {
        this.hourly = hourly;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", currently=" + currently +
                ", hourly=" + hourly +
                '}';
    }
}
