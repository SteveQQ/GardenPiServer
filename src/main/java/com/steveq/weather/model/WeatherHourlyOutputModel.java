package com.steveq.weather.model;

import java.util.List;

/**
 * Created by Adam on 2017-08-13.
 */
public class WeatherHourlyOutputModel {
    private String summary;
    private String icon;
    private ForecastData data;

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

    public ForecastData getData() {
        return data;
    }

    public void setData(ForecastData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WeatherHourlyOutputModel{" +
                "summary='" + summary + '\'' +
                ", icon='" + icon + '\'' +
                ", data=" + data +
                '}';
    }
}
