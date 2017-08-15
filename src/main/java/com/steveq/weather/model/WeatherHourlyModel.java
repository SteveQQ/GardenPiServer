package com.steveq.weather.model;

import com.steveq.weather.model.ForecastData;

import java.util.List;

/**
 * Created by Adam on 2017-08-13.
 */
public class WeatherHourlyModel {
    private String summary;
    private String icon;
    private List<ForecastData> data;

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

    public List<ForecastData> getData() {
        return data;
    }

    public void setData(List<ForecastData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WeatherHourlyModel{" +
                "summary='" + summary + '\'' +
                ", icon='" + icon + '\'' +
                ", data=" + data +
                '}';
    }
}
