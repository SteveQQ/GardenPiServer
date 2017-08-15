package com.steveq.weather;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.steveq.controllers.WeatherMethodController;
import com.steveq.utils.FileUtils;
import com.steveq.weather.model.WeatherModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Adam on 2017-08-13.
 */
public class WeatherController implements Callback<WeatherModel> {
    public static WeatherModel currentWeather;
    private static final String BASE_URL = "https://api.darksky.net/";
    Gson gson;

    public WeatherController() {
        gson = new GsonBuilder().setLenient().create();
    }

    public void getWeather(String coords){
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();
        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

        Call<WeatherModel> call = weatherAPI.getWeather(FileUtils.getApiKey(), coords);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
        System.out.println("RESPONSE : " + response.isSuccessful());
        if(response.isSuccessful()){
            System.out.println(response.body());
            currentWeather = response.body();
        } else {
            System.out.println(response.errorBody());
            currentWeather = new WeatherModel();
        }
        WeatherMethodController.isProcessing = false;
    }

    @Override
    public void onFailure(Call<WeatherModel> call, Throwable t) {
        t.printStackTrace();
        currentWeather = new WeatherModel();
    }
}
