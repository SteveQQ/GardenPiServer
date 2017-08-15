package com.steveq.controllers;

import com.steveq.communication.JsonProcessor;
import com.steveq.communication.models.*;
import com.steveq.weather.WeatherController;
import com.steveq.weather.model.WeatherHourlyOutputModel;
import com.steveq.weather.model.WeatherModel;
import com.steveq.weather.model.WeatherOutputModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 2017-08-13.
 */
public class WeatherMethodController implements Controller {
    private WeatherController weatherController;
    public static Boolean isProcessing=true;

    public WeatherMethodController(){
        this.weatherController = new WeatherController();
    }

    @Override
    public ToClientResponse processRequest(FromClientRequest request) {
        weatherController.getWeather(request.getCoords());

        while(isProcessing){}

        ToClientResponse response = new ToClientResponse();
        response.setMethod(JsonProcessor.Method.WEATHER.toString());

        response.setSections(new ArrayList<>());
        response.setWeather(prepareWeatherData(WeatherController.currentWeather));

        return response;
    }

    private WeatherOutputModel prepareWeatherData(WeatherModel data){
        if(data.getCurrently() != null){

            WeatherOutputModel wom = new WeatherOutputModel();
            wom.setLatitude(data.getLatitude());
            wom.setLongitude(data.getLongitude());
            wom.setCurrently(data.getCurrently());

            WeatherHourlyOutputModel whom = new WeatherHourlyOutputModel();
            whom.setSummary(data.getHourly().getSummary());
            whom.setIcon(data.getHourly().getIcon());
            whom.setData(data.getHourly().getData().get(24));

            wom.setHourly(whom);

            return wom;
        } else {
            return new WeatherOutputModel();
        }
    }
}
