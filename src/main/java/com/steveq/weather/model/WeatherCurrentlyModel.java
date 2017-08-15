package com.steveq.weather.model;

/**
 * Created by Adam on 2017-08-13.
 */
public class WeatherCurrentlyModel {
    private String summary;
    private String icon;
    private Double temperature;
    private Double humidity;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "WeatherCurrentlyModel{" +
                "summary='" + summary + '\'' +
                ", icon='" + icon + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }
}
